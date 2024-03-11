package dev.chipichapa.memestore.usecase;

import dev.chipichapa.memestore.domain.entity.Image;
import dev.chipichapa.memestore.domain.entity.ImageTag;
import dev.chipichapa.memestore.dto.tags.GetMemeTagsResponse;
import dev.chipichapa.memestore.exception.ResourceNotFoundException;
import dev.chipichapa.memestore.repository.AlbumRepository;
import dev.chipichapa.memestore.repository.ImageTagRepository;
import dev.chipichapa.memestore.service.ifc.ImageService;
import dev.chipichapa.memestore.usecase.ifc.MemeTagsUseCase;
import dev.chipichapa.memestore.utils.mapper.ImageTagsToGetMemeTagsResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemeTagsUseCaseImpl implements MemeTagsUseCase {

    private final ImageService imageService;

    private final ImageTagRepository imageTagRepository;
    private final AlbumRepository albumRepository;

    private final ImageTagsToGetMemeTagsResponseMapper imageTagsToGetMemeTagsResponseMapper;

    @Override
    public GetMemeTagsResponse getMemeTags(Long memeId, Long galleryId) {

        Image image = imageService.getById(memeId);

        checkImageContainsInAlbumOrThrow(galleryId, memeId);

        List<ImageTag> tags = imageTagRepository.findByImage(image);

        return imageTagsToGetMemeTagsResponseMapper.toResponse(tags);
    }

    private void checkImageContainsInAlbumOrThrow(Long albumId, Long imageId) {
        if (!albumIsContainsImage(albumId, imageId)) {
            throw new ResourceNotFoundException("Album is not contains this image");
        }
    }

    private boolean albumIsContainsImage(Long albumId, Long imageId) {
        return albumRepository.existsImageById(albumId, imageId);
    }
}
