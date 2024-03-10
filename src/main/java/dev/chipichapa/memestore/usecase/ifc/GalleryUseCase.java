package dev.chipichapa.memestore.usecase.ifc;

import dev.chipichapa.memestore.domain.model.Gallery;
import dev.chipichapa.memestore.dto.gallery.ContributorsGallery;
import dev.chipichapa.memestore.dto.gallery.GalleryCreateRequest;

public interface GalleryUseCase {
    Gallery create(GalleryCreateRequest request);
    Gallery getById(int id);
    Gallery saveGalleryChanges(GalleryCreateRequest galleryChanges, int id);
    boolean deleteGallery(int id);
    Gallery addContributors(int id, ContributorsGallery contributorsGallery);
    Gallery deleteContributors(int id, ContributorsGallery contributorsGallery);
}
