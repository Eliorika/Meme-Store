package dev.chipichapa.memestore.usecase.ifc;

import dev.chipichapa.memestore.dto.meme.CreateMemeRequest;
import dev.chipichapa.memestore.dto.meme.CreateMemeResponse;
import dev.chipichapa.memestore.dto.meme.GetMemeRequest;
import dev.chipichapa.memestore.dto.meme.GetMemeResponse;

public interface MemeUseCase {
    CreateMemeResponse create(CreateMemeRequest createRequest);

    GetMemeResponse get(GetMemeRequest getRequest);
}
