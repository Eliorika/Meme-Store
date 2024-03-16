package dev.chipichapa.memestore.dto.auth;

import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;


public record JwtRequest(
        @NonNull String fullName,
        @NonNull String username
) {

}
