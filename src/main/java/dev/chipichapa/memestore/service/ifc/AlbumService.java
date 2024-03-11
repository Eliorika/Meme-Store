package dev.chipichapa.memestore.service.ifc;

import dev.chipichapa.memestore.domain.entity.Album;
import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.dto.gallery.ContributorsGallery;
import dev.chipichapa.memestore.dto.gallery.GalleryCreateRequest;

import java.util.List;

public interface AlbumService {
    Album getGalleryById(int id);

    Album saveGallery(GalleryCreateRequest galleryCreateRequest, User user);
    Album saveChangesGallery(GalleryCreateRequest request, int id);
    void deleteGallery(int id);

    List<Album> getAllByUser(User user);
}
