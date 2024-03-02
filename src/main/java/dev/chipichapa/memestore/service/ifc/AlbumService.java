package dev.chipichapa.memestore.service.ifc;

import dev.chipichapa.memestore.domain.entity.Album;
import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.dto.gallery.GalleryCreateRequest;

public interface AlbumService {
    Album getGalleryById(int id);

    Album saveGallery(GalleryCreateRequest galleryCreateRequest, User user);
}
