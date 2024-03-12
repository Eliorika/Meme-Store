package dev.chipichapa.memestore.usecase.ifc;

import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.domain.model.Gallery;
import dev.chipichapa.memestore.dto.gallery.ContributorsGallery;
import dev.chipichapa.memestore.dto.gallery.GalleryCreateRequest;

import java.util.List;

public interface GalleryUseCase {
    Gallery create(GalleryCreateRequest request);
    Gallery getById(int id);
    Gallery saveGalleryChanges(GalleryCreateRequest galleryChanges, int id);
    boolean deleteGallery(int id);

    List<Gallery> getAllForUser(User user);

    Gallery addContributors(int id, ContributorsGallery contributorsGallery);
    Gallery deleteContributors(int id, ContributorsGallery contributorsGallery);
    List<Gallery> getAll();
}
