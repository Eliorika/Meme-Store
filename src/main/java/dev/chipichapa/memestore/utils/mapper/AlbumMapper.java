package dev.chipichapa.memestore.utils.mapper;

import dev.chipichapa.memestore.domain.entity.Album;
import dev.chipichapa.memestore.domain.entity.Image;
import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.domain.model.Gallery;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AlbumMapper {

    public static Gallery map(Album album) {
        var res = new Gallery(
                album.getAuthor().getId(),
                album.getContributors().stream().map(User::getId).collect(Collectors.toSet()),
                album.getName(),
                album.getDescription(),
                album.getVisible(),
                false,
                false,
                album.getImages()
                        .stream().min(Comparator.comparing(Image::getCreatedAt))
                        .orElseGet(Image::new)
                        .getId()
        );
        res.setId(album.getId());
        return res;
    }
    public static List<Gallery> map(List<Album> albums) {
        return albums.stream().map(AlbumMapper::map).toList();
    }
}
