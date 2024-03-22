package dev.chipichapa.memestore.usecase.ifc;

import dev.chipichapa.memestore.dto.asset.*;

public interface AssetsUseCase {
    AssetUploadResponse upload(AssetUploadRequest uploadRequest);

    AssetGetResponse get(String assetTicket);
    AssetGetResponse getById(long id);

    SuggestionsTagsResponse getSuggestTags(String assetTicket);

    AssetGetInfoResponse getAssetInfoById(Integer id);

    AssetGetInfoResponse getAssetInfoByTicket(String assetTicket);

}
