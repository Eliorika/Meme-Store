package dev.chipichapa.memestore.utils.mapper;

import dev.chipichapa.memestore.domain.entity.Album;
import dev.chipichapa.memestore.domain.model.Gallery;

import java.util.List;

public class AlbumMapper {

    public static Gallery map(Album album) {
        var res = new Gallery(
                album.getAuthor().getId(),
                null,
                album.getName(),
                album.getDescription(),
                album.getVisible(),
                false,
                false);
        res.setId(album.getId());
        return res;
    }
    public static List<Gallery> map(List<Album> albums) {
        return albums.stream().map(AlbumMapper::map).toList();
    }
}
