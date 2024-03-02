package dev.chipichapa.memestore.utils.mapper;

import dev.chipichapa.memestore.domain.entity.Album;
import dev.chipichapa.memestore.domain.model.Gallery;

public class AlbumMapper {

    public static Gallery map(Album album) {
        return new Gallery(
                album.getAuthor().getId(),
                null,
                album.getName(),
                album.getDescription(),
                true,
                false,
                false);
    }
}
