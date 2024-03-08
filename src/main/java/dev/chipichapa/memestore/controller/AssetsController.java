package dev.chipichapa.memestore.controller;

import dev.chipichapa.memestore.domain.dto.BasicApiResponse;
import dev.chipichapa.memestore.domain.enumerated.AssetType;
import dev.chipichapa.memestore.dto.asset.*;
import dev.chipichapa.memestore.usecase.ifc.AssetsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLConnection;

@RestController
@RequestMapping("/api/v1/asset")
@RequiredArgsConstructor
public class AssetsController {

    private final AssetsUseCase assetsUseCase;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BasicApiResponse<AssetUploadResponse>> upload(@RequestParam(name = "type") AssetType type,
                                                                        @RequestBody MultipartFile file) {
        AssetUploadResponse response = assetsUseCase.upload(
                new AssetUploadRequest(type, file)
        );

        return new ResponseEntity<>(new BasicApiResponse<>(false, response), HttpStatus.OK);
    }

    @GetMapping(value = "/suggest_tags")
    public ResponseEntity<BasicApiResponse<SuggestionsTagsResponse>> getSuggestTags(
            @RequestParam(name = "asset") String temporaryTicket) {
        SuggestionsTagsResponse response = assetsUseCase.getSuggestTags(temporaryTicket);

        return new ResponseEntity<>(new BasicApiResponse<>(false, response), HttpStatus.OK);
    }

    @GetMapping("/get_info")
    public ResponseEntity<BasicApiResponse<AssetGetInfoResponse>> getInfo(
            @RequestParam(name = "id", required = false) Integer assetId,
            @RequestParam(name = "asset", required = false) String ticket
    ) {
        AssetGetInfoResponse response;
        if (assetId != null) {
            response = assetsUseCase.getAssetInfoById(assetId);
        } else if (ticket != null) {
            response = assetsUseCase.getAssetInfoByTicket(ticket);
        } else {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(new BasicApiResponse<>(false, response), HttpStatus.OK);
    }

    @GetMapping("/get/{uuid}")
    public ResponseEntity<byte[]> get(@PathVariable("uuid") String uuid) {
        AssetGetResponse response = assetsUseCase.get(uuid);
        String contentType = URLConnection.guessContentTypeFromName("." + response.extension());

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(response.file());
    }

}
