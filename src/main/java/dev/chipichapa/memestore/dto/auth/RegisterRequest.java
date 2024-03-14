package dev.chipichapa.memestore.dto.auth;

import org.springframework.lang.NonNull;

public record RegisterRequest(@NonNull Long tgId,
                              @NonNull String username,
                              @NonNull String firstName,
                              @NonNull String lastName) {
}
