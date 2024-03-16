package dev.chipichapa.memestore.usecase;

import com.amazonaws.services.kms.model.NotFoundException;
import dev.chipichapa.memestore.domain.entity.Image;
import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.domain.enumerated.RecommendationMarks;
import dev.chipichapa.memestore.dto.meme.*;
import dev.chipichapa.memestore.dto.recommedation.ItemRabbitDTO;
import dev.chipichapa.memestore.dto.recommedation.MarkRabbitDTO;
import dev.chipichapa.memestore.exception.ResourceNotFoundException;
import dev.chipichapa.memestore.repository.AlbumRepository;
import dev.chipichapa.memestore.repository.DraftRepository;
import dev.chipichapa.memestore.repository.ImageRepository;
import dev.chipichapa.memestore.repository.TagRepository;
import dev.chipichapa.memestore.security.exception.AccessDeniedException;
import dev.chipichapa.memestore.service.ifc.AlbumService;
import dev.chipichapa.memestore.service.ifc.ImageService;
import dev.chipichapa.memestore.service.ifc.RecommendationRabbitProducer;
import dev.chipichapa.memestore.service.ifc.TagService;
import dev.chipichapa.memestore.usecase.ifc.MemeUseCase;
import dev.chipichapa.memestore.utils.AuthUtils;
import dev.chipichapa.memestore.utils.mapper.ImageToCreateMemeResponseMapper;
import dev.chipichapa.memestore.utils.mapper.ImageToMemeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemeUseCaseImpl implements MemeUseCase {

    private final ImageService imageService;
    private final TagService tagService;
    private final AlbumService albumService;

    private final ImageRepository imageRepository;
    private final TagRepository tagRepository;
    private final DraftRepository draftRepository;
    private final AlbumRepository albumRepository;

    private final ImageToMemeMapper imageToMemeMapper;
    private final AuthUtils authUtils;

    private final RecommendationRabbitProducer recommendationRabbitProducer;

    @Override
    @Transactional
    public CreateMemeResponse create(CreateMemeRequest createRequest) {
        String assetTicket = createRequest.getAssetTicket();

        boolean isDraftExist = draftRepository.existsById(UUID.fromString(assetTicket));
        if (!isDraftExist) {
            throw new NotFoundException("Draft with this ticket is not found. Maybe meme is already created");
        }

        Image meme = imageService.getByTicket(assetTicket);
        Image image = setImageFieldsFromRequest(meme, createRequest);

        Image savedImage = imageRepository.save(image);

        saveImageToAlbumOrThrow(createRequest.getGalleryId(), savedImage);

        List<Integer> tagsIds = tagService
                .addTagsToImageAndReturnTagsIds(savedImage, createRequest.getTags());

        draftRepository.deleteById(UUID.fromString(assetTicket));

        recommendationRabbitProducer.sendItem(new ItemRabbitDTO(savedImage.getId()));
        recommendationRabbitProducer.sendMark(new MarkRabbitDTO(
                savedImage.getAuthor().getId(),
                savedImage.getId(),
                RecommendationMarks.ADD_MEME.getMark()
        ));

        return ImageToCreateMemeResponseMapper.toResponse(image, tagsIds);
    }

    @Override
    @Transactional
    public GetMemeResponse get(GetMemeRequest getRequest) {
        long memeId = getRequest.memeId();
        long albumId = getRequest.galleryId();

        if (!albumService.isVisibleAlbum(albumId)) {
            Optional<UserDetails> principal = authUtils.getUserDetailsOrNull();
            if(principal.isEmpty()){
                throw new AccessDeniedException("U can't get meme from private album without auth");
            }

            User user = authUtils.getUserEntity();
            Set<Long> contributors = albumService
                    .getAllContributorIdsIncludeOwner(albumId);

            if (contributors.contains(user.getId())){
                throw new AccessDeniedException();
            }
        }

        Image image = getMemeById(memeId);
        checkImageContainsInAlbumOrThrow(albumId, memeId);
        List<Integer> tagIds = getImageTagIds(image);

        recommendationRabbitProducer.sendMark(new MarkRabbitDTO(
                image.getAuthor().getId(),
                image.getId(),
                RecommendationMarks.OPEN_MEME.getMark()
        ));

        return new GetMemeResponse(imageToMemeMapper.toMeme(image, tagIds));
    }


    @Override
    @Transactional
    public UpdateMemeResponse update(UpdateMemeRequest request, Long memeId) {
        User user = authUtils.getUserEntity();
        Image image = getMemeById(memeId);

        if (image.getAuthor().getId() != user.getId()){
            throw new AccessDeniedException("This user is not owner");
        }

        image.setDescription(request.description())
                .setTitle(request.title());

        Image saved = imageRepository.save(image);
        List<Integer> tagsIds = getImageTagIds(saved);

        return new UpdateMemeResponse(imageToMemeMapper.toMeme(image, tagsIds));
    }

    private Image getMemeById(Long memeId) {
        return imageService.getById(memeId);
    }

    private List<Integer> getImageTagIds(Image image) {
        return tagRepository.findTagsIdsByImageId(image.getId());
    }

    private void checkImageContainsInAlbumOrThrow(Long albumId, Long imageId) {
        if (!albumIsContainsImage(albumId, imageId)) {
            throw new ResourceNotFoundException("Album is not contains this image");
        }
    }

    private boolean albumIsContainsImage(Long albumId, Long imageId) {
        return albumRepository.existsImageById(albumId, imageId);
    }

    private void saveImageToAlbumOrThrow(Integer albumId, Image image) {
        boolean albumIsExist = albumRepository.existsById(albumId);
        if (!albumIsExist) {
            throw new NotFoundException(String.format("Album with id = (%d) is not found", albumId));
        }
        imageRepository.saveImage(albumId, image.getId());
    }

    private Image setImageFieldsFromRequest(Image image, CreateMemeRequest request) {
        return image
                .setTitle(request.getTitle())
                .setDescription(request.getDescription());
    }
}
