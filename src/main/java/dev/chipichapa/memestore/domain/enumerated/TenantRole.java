package dev.chipichapa.memestore.domain.enumerated;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum TenantRole {
    ANONYMOUS(0),
    READ_ONLY(1),
    PRIVATE_UPLOADER(2),
    PUBLIC_UPLOADER(3),
    VOTER(4),
    TAG_ASSIGNER(5),
    TAG_CREATOR(6),
    PRIVATE_ALBUM_CREATOR(7),
    PUBLIC_ALBUM_CREATOR(8),
    GLOBAL_ADMIN(256),
    DEBUG_VIEW(257),
    TAG_VOTE_MODERATOR(258),
    TAG_MODERATOR(259),
    UPLOAD_MODERATOR(260),
    PUBLIC_ALBUM_MODERATOR(261),
    PRIVATE_ALBUM_MODERATOR(262),
    USER_TENANT_MODERATOR(263),
    ALLOW_DELETION_OF_USER(264),
    CREATE_USER_TENANTS(265),
    AUTHORIZE_USER_TENANTS(266);

    private final int value;

    TenantRole(int value) {
        this.value = value;
    }

    public static List<Integer> getAllRoles() {
        return Arrays.stream(TenantRole.values())
                .map(tenantRole -> tenantRole.value)
                .toList();
    }
}
