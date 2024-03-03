package dev.chipichapa.memestore.usecase.ifc;

import dev.chipichapa.memestore.dto.auth.RegisterRequest;
import dev.chipichapa.memestore.dto.auth.TgRegisterRequest;

public interface UserRegisterUseCase {
    void register(RegisterRequest request);
    void registerTg(TgRegisterRequest request);
}
