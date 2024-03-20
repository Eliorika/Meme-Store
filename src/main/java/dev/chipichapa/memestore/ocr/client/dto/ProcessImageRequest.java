package dev.chipichapa.memestore.ocr.client.dto;

import java.util.List;

public record ProcessImageRequest(String mimeType,
                                  List<String> languageCodes,
                                  String model,
                                  String content) {
}
