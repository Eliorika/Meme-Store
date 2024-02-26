package dev.chipichapa.memestore.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Meme extends BaseModel{
    private long authorId;
    private long assetId;
    private List<Integer> tagIds;
    private String title;
    private String description;

    public Meme(long id, long authorId, long assetId, List<Integer> tagIds, String title, String description) {
        super(id);
        this.authorId = authorId;
        this.assetId = assetId;
        this.tagIds = tagIds;
        this.title = title;
        this.description = description;
    }
}
