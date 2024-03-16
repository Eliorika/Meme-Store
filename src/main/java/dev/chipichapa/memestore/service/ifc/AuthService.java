package dev.chipichapa.memestore.service.ifc;

import dev.chipichapa.memestore.dto.auth.JwtRequest;
import dev.chipichapa.memestore.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse auth(JwtRequest request, Long telegramId);

    JwtResponse refresh(String refreshToken);
}
