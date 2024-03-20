package dev.chipichapa.memestore.usecase.ifc;

import dev.chipichapa.memestore.dto.meme.*;

import java.util.List;
import java.util.Set;

public interface MemeUseCase {
    CreateMemeResponse create(CreateMemeRequest createRequest);

    GetMemeResponse get(GetMemeRequest getRequest);

    UpdateMemeResponse update(UpdateMemeRequest request ,Long memeId);
    Set<GetMemeResponse> getMemesFromGallery(Integer galleryId);
    GetMemeResponse getById(Integer id);
}
