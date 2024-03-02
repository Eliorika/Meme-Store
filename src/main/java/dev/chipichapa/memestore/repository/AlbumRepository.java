package dev.chipichapa.memestore.repository;

import dev.chipichapa.memestore.domain.entity.Album;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
public interface AlbumRepository extends CrudRepository<Album, Integer> {

    Optional<Album> findByGalleryId(Integer id);
}
