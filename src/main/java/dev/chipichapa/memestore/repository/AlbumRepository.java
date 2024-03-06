package dev.chipichapa.memestore.repository;

import dev.chipichapa.memestore.domain.entity.Album;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
public interface AlbumRepository extends CrudRepository<Album, Integer> {

    List<Album> findAllByAuthorId(Long id);

}
