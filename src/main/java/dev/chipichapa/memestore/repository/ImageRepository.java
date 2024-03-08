package dev.chipichapa.memestore.repository;

import dev.chipichapa.memestore.domain.entity.Image;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
    Optional<Image> findByUuid(UUID uuid);

    @Modifying
    @Query(value = """
            INSERT INTO album_images AS ai (image_id, album_id) 
            VALUES (:imageId, :albumId)
            """, nativeQuery = true)
    void saveImage(@Param("albumId") Integer albumId, @Param("imageId") Integer imageId);
}
