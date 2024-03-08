package dev.chipichapa.memestore.usecase.ifc;

import dev.chipichapa.memestore.dto.meme.*;

public interface MemeUseCase {
    CreateMemeResponse create(CreateMemeRequest createRequest);

    GetMemeResponse get(GetMemeRequest getRequest);

    UpdateMemeResponse update(UpdateMemeRequest request ,Long memeId);
}
