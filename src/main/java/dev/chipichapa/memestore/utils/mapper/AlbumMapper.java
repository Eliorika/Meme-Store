package dev.chipichapa.memestore.utils.mapper;

import dev.chipichapa.memestore.domain.entity.Album;
import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.domain.model.Gallery;

import java.util.List;
import java.util.stream.Collectors;

public class AlbumMapper {

    public static Gallery map(Album album) {
        return new Gallery(
                album.getId(),
                album.getContributors().stream().map(User::getId).collect(Collectors.toSet()),
                album.getName(),
                album.getDescription(),
                album.getStatus(),
                false,
                false);
    }

    public static List<Gallery> map(List<Album> albums) {
        return albums.stream().map(AlbumMapper::map).toList();
    }
}
