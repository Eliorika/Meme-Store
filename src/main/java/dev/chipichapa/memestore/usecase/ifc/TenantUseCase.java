package dev.chipichapa.memestore.usecase.ifc;

import dev.chipichapa.memestore.domain.model.tenant.Tenant;
import dev.chipichapa.memestore.domain.model.tenant.TenantProfile;

public interface TenantUseCase {

    Tenant getTenant();
    Tenant getTenantById(Long id);

    TenantProfile getTenantProfile(Long id);
}
