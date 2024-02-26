package dev.chipichapa.memestore.domain.enumerated;

import lombok.Getter;

@Getter
public enum TenantOrigin {
    INTERNAL_SYSTEM("internal/system"),
    INTERNAL_USER("internal/user"),
    INTERNAL_SERVICE("internal/service"),
    EXTERNAL_TELEGRAM("external/tg");

    TenantOrigin(String value) {
        this.value = value;
    }

    private final String value;
}
