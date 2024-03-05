package dev.chipichapa.memestore.usecase.ifc;

import dev.chipichapa.memestore.dto.meme.CreateMemeRequest;
import dev.chipichapa.memestore.dto.meme.CreateMemeResponse;

public interface MemeUseCase {
    CreateMemeResponse create(CreateMemeRequest createRequest);
}
