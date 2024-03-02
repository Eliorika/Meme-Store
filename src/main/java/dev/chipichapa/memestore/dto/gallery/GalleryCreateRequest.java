package dev.chipichapa.memestore.dto.gallery;


public record GalleryCreateRequest(
        String name,
        String description,
        boolean isPublic
) {
}
