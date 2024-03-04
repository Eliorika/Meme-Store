package dev.chipichapa.memestore.usecase.ifc;

import dev.chipichapa.memestore.domain.entity.Album;
import dev.chipichapa.memestore.domain.model.Gallery;
import dev.chipichapa.memestore.dto.gallery.GalleryCreateRequest;

public interface GalleryUseCase {
    Gallery create(GalleryCreateRequest request);
    Gallery getById(int id);
    Gallery saveGalleryChanges(GalleryCreateRequest galleryChanges, int id);
    boolean deleteGallery(int id);
}
