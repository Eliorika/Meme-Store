package dev.chipichapa.memestore.typesense.dto;

import org.springframework.lang.Nullable;

import java.util.List;

public record File(Integer id,
                   @Nullable String description,
                   @Nullable String title,
                   @Nullable String ocrText,
                   @Nullable List<String> tags) {
}
