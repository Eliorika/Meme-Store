package dev.chipichapa.memestore.ocr.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.chipichapa.memestore.ocr.client.dto.GetNewTokenRequest;
import dev.chipichapa.memestore.ocr.client.dto.IAMTokenResponse;
import dev.chipichapa.memestore.ocr.client.dto.ProcessImageRequest;
import dev.chipichapa.memestore.ocr.config.OcrServerProperties;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class YandexOcrClient implements OcrClient {
    private final RestClient client;
    private final OcrServerProperties prop;
    public YandexOcrClient(@Qualifier("yandexClient") RestClient client, OcrServerProperties prop) {
        this.client = client;
        this.prop = prop;
    }

    @Override
    public IAMTokenResponse getNewIAMToken() {
        IAMTokenResponse response = client
                .post()
                .uri(prop.getAuthServerUrl())
                .body(new GetNewTokenRequest(prop.getOAuthToken()))
                .retrieve()
                .body(IAMTokenResponse.class);

        return response;
    }

    @Override
    @SneakyThrows
    public String processImage(ProcessImageRequest request, String IAMToken) {
        String result = client.post()
                .uri(prop.getImageProcessServerUrl())
                .body(request)
                .header("x-folder-id", prop.getFolderId())
                .header("Authorization", "Bearer " + IAMToken)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(String.class);

        String textFromImage = new ObjectMapper()
                .readTree(result)
                .get("result")
                .get("textAnnotation")
                .get("fullText").asText();

        return textFromImage;
    }
}
