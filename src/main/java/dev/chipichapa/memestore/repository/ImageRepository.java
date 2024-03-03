package dev.chipichapa.memestore.repository;

import dev.chipichapa.memestore.domain.entity.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
}
