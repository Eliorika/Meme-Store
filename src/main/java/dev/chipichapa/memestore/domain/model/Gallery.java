package dev.chipichapa.memestore.domain.model;

import dev.chipichapa.memestore.domain.entity.Image;
import dev.chipichapa.memestore.domain.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class Gallery extends BaseModel{
    private long ownerId;
    private Set<Long> contributorsIds;
    private String name;
    private String description;
    private boolean isPublic;
    private boolean ownerByExtremist;
    private boolean currentTenantCanEdit;
}
