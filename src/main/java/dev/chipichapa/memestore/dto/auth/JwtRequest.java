package dev.chipichapa.memestore.dto.auth;

import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;


public record JwtRequest(
        @NonNull Long tgId,
        @Nullable String username,
        @Nullable String firstName,
        @Nullable String lastName
) {

}
