package dev.chipichapa.memestore.dto.auth;

import lombok.Data;

@Data
public class JwtRefreshRequest {
    private String refreshToken;
}
