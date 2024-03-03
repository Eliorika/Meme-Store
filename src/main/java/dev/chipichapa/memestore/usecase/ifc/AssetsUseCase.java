package dev.chipichapa.memestore.usecase.ifc;

import dev.chipichapa.memestore.dto.asset.AssetUploadRequest;
import dev.chipichapa.memestore.dto.asset.AssetUploadResponse;
import dev.chipichapa.memestore.dto.asset.SuggestionsTagsResponse;

public interface AssetsUseCase {
    AssetUploadResponse upload(AssetUploadRequest uploadRequest);

    SuggestionsTagsResponse getSuggestTags(String assetTicket);
}
