package dev.chipichapa.memestore.ocr.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class OcrBeans {

    private final OcrServerProperties ocrServerProperties;

    @Bean
    RestClient yandexClient() {
        return RestClient.builder()
                .defaultHeader("x-folder-id", ocrServerProperties.getFolderId())
                .build();
    }
}
