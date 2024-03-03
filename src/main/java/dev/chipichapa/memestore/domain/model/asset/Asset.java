package dev.chipichapa.memestore.domain.model.asset;

import dev.chipichapa.memestore.domain.enumerated.AssetType;
import dev.chipichapa.memestore.domain.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Asset extends BaseModel {
    private String blurhash;
    private AssetType type;

    public Asset(long id, String blurhash, AssetType type) {
        super(id);
        this.blurhash = blurhash;
        this.type = type;
    }
}
