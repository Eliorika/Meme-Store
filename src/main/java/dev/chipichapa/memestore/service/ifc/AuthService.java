package dev.chipichapa.memestore.service.ifc;

import dev.chipichapa.memestore.dto.JwtRequest;
import dev.chipichapa.memestore.dto.JwtResponse;

public interface AuthService {

    JwtResponse login(JwtRequest request);

    JwtResponse refresh(String refreshToken);
}
