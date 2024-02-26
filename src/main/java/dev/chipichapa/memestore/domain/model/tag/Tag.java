package dev.chipichapa.memestore.domain.model.tag;

import dev.chipichapa.memestore.domain.model.BaseModel;
import lombok.Getter;

@Getter
public class Tag extends BaseModel {
    private final String name;

    public Tag(long id, String name) {
        super(id);
        this.name = name;
    }
}
