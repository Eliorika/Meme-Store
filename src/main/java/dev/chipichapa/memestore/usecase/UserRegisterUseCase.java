package dev.chipichapa.memestore.usecase;

import dev.chipichapa.memestore.dto.auth.RegisterRequest;

public interface UserRegisterUseCase {
    void register(RegisterRequest request);
}
