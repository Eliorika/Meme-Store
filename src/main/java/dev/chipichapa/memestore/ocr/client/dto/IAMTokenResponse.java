package dev.chipichapa.memestore.ocr.client.dto;

import java.time.Instant;

public record IAMTokenResponse(String iamToken, Instant expiresAt) {
}
