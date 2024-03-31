package dev.chipichapa.memestore.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Gallery extends BaseModel{
    private long ownerId;
    private Set<Long> contributorIds;
    private String name;
    private String description;

    @JsonProperty(value = "public")
    private boolean isPublic;
    private boolean ownerByExtremist;
    private boolean currentTenantCanEdit;
}
