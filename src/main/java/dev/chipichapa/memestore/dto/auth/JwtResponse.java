package dev.chipichapa.memestore.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private Long id;
    private Long tgId;
    private String accessToken;
    private String refreshToken;
}
