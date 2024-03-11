package dev.chipichapa.memestore.usecase.ifc;


import dev.chipichapa.memestore.dto.tags.GetMemeTagsResponse;

public interface MemeTagsUseCase {
    GetMemeTagsResponse getMemeTags(Long memeId, Long galleryId);

}
