package dev.chipichapa.memestore.domain.model.tenant;

import dev.chipichapa.memestore.domain.enumerated.TenantOrigin;
import dev.chipichapa.memestore.domain.enumerated.TenantRole;
import dev.chipichapa.memestore.domain.enumerated.TenantType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class TenantProfile extends Tenant{
    private List<Integer> publicGalleries;
    private List<Integer> privateGalleries;

    public TenantProfile(List<Integer> publicGalleries, List<Integer> privateGalleries) {
        this.publicGalleries = publicGalleries;
        this.privateGalleries = privateGalleries;
    }

    public TenantProfile(long id, List<TenantRole> roles, TenantType type, TenantOrigin origin, String displayName, String uniqueName, boolean isRestricted, List<Integer> publicGalleries, List<Integer> privateGalleries) {
        super(id, roles, type, origin, displayName, uniqueName, isRestricted);
        this.publicGalleries = publicGalleries;
        this.privateGalleries = privateGalleries;
    }

    public TenantProfile(long id, List<TenantRole> roles, TenantType type, TenantOrigin origin, String displayName, String uniqueName, boolean isRestricted, long extremistDate, long foreignAgentDate, List<Integer> publicGalleries, List<Integer> privateGalleries) {
        super(id, roles, type, origin, displayName, uniqueName, isRestricted, extremistDate, foreignAgentDate);
        this.publicGalleries = publicGalleries;
        this.privateGalleries = privateGalleries;
    }
}
