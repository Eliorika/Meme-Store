package dev.chipichapa.memestore.typesense.dto;

import java.util.List;

public record SavedMeme(Integer id, String title, String description, List<String> tags) {
}
