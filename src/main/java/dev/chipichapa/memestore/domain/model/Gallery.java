package dev.chipichapa.memestore.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Gallery extends BaseModel{
    private long ownerId;
    private List<Long> contributorIds;
    private String name;
    private String description;
    private boolean isPublic;
    private boolean ownerByExtremist;
    private boolean currentTenantCanEdit;


}
