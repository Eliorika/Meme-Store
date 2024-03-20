package dev.chipichapa.memestore.ocr.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("ocr")
public class OcrServerProperties {
    private String oAuthToken;
    private String authServerUrl;
    private String imageProcessServerUrl;
    private String folderId;
}
