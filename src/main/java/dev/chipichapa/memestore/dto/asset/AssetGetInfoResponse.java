package dev.chipichapa.memestore.dto.asset;

import dev.chipichapa.memestore.domain.enumerated.AssetType;
import dev.chipichapa.memestore.domain.model.asset.Image;


public class AssetGetInfoResponse extends Image {
    public AssetGetInfoResponse(long id, String blurhash, AssetType type, String uri) {
        super(id, blurhash, type, uri);
    }
}
