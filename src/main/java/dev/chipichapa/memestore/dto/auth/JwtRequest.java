package dev.chipichapa.memestore.dto.auth;

import org.springframework.lang.NonNull;


public record JwtRequest(
        @NonNull String fullName,
        @NonNull String username
) {

}
