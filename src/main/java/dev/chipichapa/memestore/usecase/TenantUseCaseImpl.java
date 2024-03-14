package dev.chipichapa.memestore.usecase;

import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.domain.enumerated.TenantOrigin;
import dev.chipichapa.memestore.domain.enumerated.TenantRole;
import dev.chipichapa.memestore.domain.enumerated.TenantType;
import dev.chipichapa.memestore.domain.model.tenant.Tenant;
import dev.chipichapa.memestore.usecase.ifc.TenantUseCase;
import dev.chipichapa.memestore.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantUseCaseImpl implements TenantUseCase {

    private final AuthUtils authUtils;

    @Override
    public Tenant getTenant() {
        User user = authUtils.getUserEntity();

        return new Tenant(user.getId(),
                TenantRole.getAllRoles(),
                TenantType.USER,
                TenantOrigin.EXTERNAL_TELEGRAM,
                user.getDisplayName(),
                user.getUsername(),
                false);
    }
}
