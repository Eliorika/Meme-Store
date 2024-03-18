package dev.chipichapa.memestore.usecase.ifc;

import dev.chipichapa.memestore.dto.asset.*;

public interface AssetsUseCase {
    AssetUploadResponse upload(AssetUploadRequest uploadRequest);

    AssetGetResponse get(String assetTicket);

    SuggestionsTagsResponse getSuggestTags(String assetTicket);

    AssetGetInfoResponse getAssetInfoById(Integer id);

    AssetGetInfoResponse getAssetInfoByTicket(String assetTicket);

    AssetGetResponse getById(Long id);

}
