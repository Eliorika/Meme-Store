package dev.chipichapa.memestore.domain.model.tenant;

import dev.chipichapa.memestore.domain.enumerated.TenantOrigin;
import dev.chipichapa.memestore.domain.enumerated.TenantRole;
import dev.chipichapa.memestore.domain.enumerated.TenantType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@AllArgsConstructor
public class TenantProfile{
    private List<Long> publicGalleries;
    private List<Long> privateGalleries;


}
