package dev.chipichapa.memestore.ocr.service;

import dev.chipichapa.memestore.ocr.dto.OcrImage;

public interface OcrService {

    String extractTextFromImage(OcrImage image);

    boolean isTokenExpireSoon();

    void requestAndSaveNewToken();

    String getIamToken();
}
