package dev.chipichapa.memestore.service.ifc;

import dev.chipichapa.memestore.domain.entity.Album;
import dev.chipichapa.memestore.domain.entity.Image;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    Image getById(Long id);

    Image getByTicket(String ticket);

    List<Image> getLastPublicImages(int offset, int limit);
    List<Image> getImagesFromAlbum(Album album, int offset, int limit);

    Optional<Image> getImageIfPublicAlbumsExists(long imageId);


}
