package dev.chipichapa.memestore.domain.model.tenant;

import dev.chipichapa.memestore.domain.enumerated.TenantOrigin;
import dev.chipichapa.memestore.domain.enumerated.TenantRole;
import dev.chipichapa.memestore.domain.enumerated.TenantType;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class TenantProfile extends Tenant{
    private List<Long> publicGalleries;
    private List<Long> privateGalleries;

    public TenantProfile(Tenant tenant, List<Long> publicGalleries, List<Long> privateGalleries) {
        super(tenant.getId(), tenant.getRoles(), tenant.getType(), tenant.getOrigin(), tenant.getDisplayName(), tenant.getUniqueName(), tenant.isRestricted(), tenant.getExtremistDate(), tenant.getForeignAgentDate());
        this.publicGalleries = publicGalleries;
        this.privateGalleries = privateGalleries;
    }
}
