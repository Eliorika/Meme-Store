package dev.chipichapa.memestore.ocr.service;

import dev.chipichapa.memestore.exception.AppException;
import dev.chipichapa.memestore.ocr.client.OcrClient;
import dev.chipichapa.memestore.ocr.client.dto.IAMTokenResponse;
import dev.chipichapa.memestore.ocr.client.dto.ProcessImageRequest;
import dev.chipichapa.memestore.ocr.client.enumarated.LanguageCode;
import dev.chipichapa.memestore.ocr.dto.OcrImage;
import dev.chipichapa.memestore.ocr.entity.OcrToken;
import dev.chipichapa.memestore.ocr.repository.OcrTokenRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OcrServiceImpl implements OcrService {

    private final OcrTokenRepository ocrTokenRepository;
    private final OcrClient ocrClient;

    @PostConstruct
    private void init() {
        checkTokenAtDbAndCreateIfNotExist();
    }

    @Override
    public String extractTextFromImage(OcrImage image) {
        String textFromImage = ocrClient.processImage(new ProcessImageRequest(
                getMimeTypeByExtension(image.extension()),
                List.of("*"),
                "page",
                imageToBase64(image.imageContent())
        ), getIamToken());

        return textFromImage;
    }

    private String getMimeTypeByExtension(String extension) {
        return URLConnection.guessContentTypeFromName("." + extension);
    }

    private String imageToBase64(byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }

    @Override
    public boolean isTokenExpireSoon() {
        OcrToken iamToken = getIamTokenOrThrow();

        Instant expiredTime = iamToken.getExpiredAt().toInstant();
        Duration duration = Duration.between(expiredTime, Instant.now());

        return duration.toHours() < 2;
    }


    @Override
    public void requestAndSaveNewToken() {
        OcrToken tokenFromDb = getIamTokenOrThrow();
        IAMTokenResponse newIAMToken = ocrClient.getNewIAMToken();

        tokenFromDb.setToken(newIAMToken.iamToken());
        tokenFromDb.setExpiredAt(Timestamp.from(newIAMToken.expiresAt()));
        ocrTokenRepository.save(tokenFromDb);
    }

    @Override
    public String getIamToken() {
        return getIamTokenOrThrow().getToken();
    }

    private OcrToken getIamTokenOrThrow() {
        return ocrTokenRepository
                .findById(1L)
                .orElseThrow(() -> new AppException("IAM token is not exist."));
    }

    private void checkTokenAtDbAndCreateIfNotExist() {
        if (ocrTokenRepository.count() == 0) {
            IAMTokenResponse iamToken = ocrClient.getNewIAMToken();
            OcrToken token = new OcrToken()
                    .setToken(iamToken.iamToken())
                    .setExpiredAt(Timestamp.from(iamToken.expiresAt()));

            ocrTokenRepository.save(token);
        }
    }
}
