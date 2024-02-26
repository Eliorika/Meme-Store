package dev.chipichapa.memestore.domain.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Gallery extends BaseModel{
    private long ownerId;
    private List<Long> contributorIds;
    private String name;
    private String description;
    private boolean isPublic;
    private boolean ownerByExtremist;
    private boolean currentTenantCanEdit;

    public Gallery(long id, long ownerId, List<Long> contributorIds, String name, String description, boolean isPublic, boolean ownerByExtremist, boolean currentTenantCanEdit) {
        super(id);
        this.ownerId = ownerId;
        this.contributorIds = contributorIds;
        this.name = name;
        this.description = description;
        this.isPublic = isPublic;
        this.ownerByExtremist = ownerByExtremist;
        this.currentTenantCanEdit = currentTenantCanEdit;
    }
}
