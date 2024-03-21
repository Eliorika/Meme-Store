package dev.chipichapa.memestore.ocr.client;

import dev.chipichapa.memestore.ocr.client.dto.IAMTokenResponse;
import dev.chipichapa.memestore.ocr.client.dto.ProcessImageRequest;

public interface OcrClient {

    IAMTokenResponse getNewIAMToken();

    String processImage(ProcessImageRequest request, String IAMToken);
}
