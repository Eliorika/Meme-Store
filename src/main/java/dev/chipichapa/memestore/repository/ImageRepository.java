package dev.chipichapa.memestore.repository;

import dev.chipichapa.memestore.domain.entity.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {

    Optional<Image> findByUuid(UUID uuid);
}
