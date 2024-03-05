package dev.chipichapa.memestore.dto.asset;

import dev.chipichapa.memestore.domain.enumerated.AssetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class AssetUploadRequest {
    private AssetType type;
    private MultipartFile file;
}
