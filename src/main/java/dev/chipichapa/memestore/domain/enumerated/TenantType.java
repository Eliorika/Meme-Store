package dev.chipichapa.memestore.domain.enumerated;

import lombok.Getter;

@Getter
public enum TenantType {

    GUEST("guest"),
    USER("user"),
    SERVICE("service");

    TenantType(String value) {
        this.value = value;
    }

    private final String value;
}
