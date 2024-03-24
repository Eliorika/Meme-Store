package dev.chipichapa.memestore.domain.model.tenant;

import dev.chipichapa.memestore.domain.enumerated.TenantOrigin;
import dev.chipichapa.memestore.domain.enumerated.TenantType;
import dev.chipichapa.memestore.domain.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Tenant extends BaseModel {
    private List<Integer> roles;
    private TenantType type;
    private TenantOrigin origin;
    private String displayName;
    private String uniqueName;
    private boolean isRestricted;
    private long extremistDate;
    private long foreignAgentDate;

    public Tenant(long id, List<Integer> roles, TenantType type, TenantOrigin origin, String displayName, String uniqueName, boolean isRestricted) {
        super(id);
        this.roles = roles;
        this.type = type;
        this.origin = origin;
        this.displayName = displayName;
        this.uniqueName = uniqueName;
        this.isRestricted = isRestricted;
    }

    public Tenant(long id, List<Integer> roles, TenantType type, TenantOrigin origin, String displayName, String uniqueName, boolean isRestricted, long extremistDate, long foreignAgentDate) {
        super(id);
        this.roles = roles;
        this.type = type;
        this.origin = origin;
        this.displayName = displayName;
        this.uniqueName = uniqueName;
        this.isRestricted = isRestricted;
        this.extremistDate = extremistDate;
        this.foreignAgentDate = foreignAgentDate;
    }


}
