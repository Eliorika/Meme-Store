package dev.chipichapa.memestore.dto;

import lombok.Data;

@Data
public class JwtRefreshRequest {
    private String refreshToken;
}
