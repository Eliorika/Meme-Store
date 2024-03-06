package dev.chipichapa.memestore.usecase;

import com.amazonaws.services.kms.model.NotFoundException;
import dev.chipichapa.memestore.domain.entity.Image;
import dev.chipichapa.memestore.dto.meme.CreateMemeRequest;
import dev.chipichapa.memestore.dto.meme.CreateMemeResponse;
import dev.chipichapa.memestore.repository.AlbumRepository;
import dev.chipichapa.memestore.repository.DraftRepository;
import dev.chipichapa.memestore.repository.ImageRepository;
import dev.chipichapa.memestore.service.ifc.ImageService;
import dev.chipichapa.memestore.service.ifc.TagService;
import dev.chipichapa.memestore.usecase.ifc.MemeUseCase;
import dev.chipichapa.memestore.utils.mapper.ImageToCreateMemeResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemeUseCaseImpl implements MemeUseCase {

    private final ImageService imageService;
    private final TagService tagService;

    private final ImageRepository imageRepository;
    private final DraftRepository draftRepository;
    private final AlbumRepository albumRepository;

    @Override
    @Transactional
    public CreateMemeResponse create(CreateMemeRequest createRequest) {
        String assetTicket = createRequest.getAssetTicket();

        boolean isDraftExist = draftRepository.existsById(UUID.fromString(assetTicket));
        if (!isDraftExist) {
            throw new NotFoundException("Draft with this ticket is not found. Maybe meme is already created");
        }

        Image meme = imageService.getByTicket(assetTicket);
        Image image = setImageFieldsFromRequest(createRequest, meme);

        Image savedImage = imageRepository.save(image);

        saveImageToAlbumOrThrow(createRequest.getGalleryId(), savedImage);

        List<Integer> tagsIds = tagService
                .addTagsToImageAndReturnTagsIds(savedImage, createRequest.getTags());

        draftRepository.deleteById(UUID.fromString(assetTicket));

        return ImageToCreateMemeResponseMapper.toResponse(image, tagsIds);
    }

    private void saveImageToAlbumOrThrow(Integer albumId, Image image) {
        boolean albumIsExist = albumRepository.existsById(albumId);
        if (!albumIsExist) {
            throw new NotFoundException(String.format("Album with id = (%d) is not found", albumId));
        }
        albumRepository.saveImage(albumId, image.getId());
    }

    private Image setImageFieldsFromRequest(CreateMemeRequest request, Image image) {
        return image
                .setTitle(request.getTitle())
                .setDescription(request.getDescription());
    }
}
