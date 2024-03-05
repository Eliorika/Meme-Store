package dev.chipichapa.memestore.repository;

import dev.chipichapa.memestore.domain.entity.Album;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AlbumRepository extends CrudRepository<Album, Integer> {
    @Modifying
    @Query(value = """
            INSERT INTO album_images AS ai (image_id, album_id) 
            VALUES (:imageId, :albumId)
            """, nativeQuery = true)
    void saveImage(@Param("albumId") Integer albumId, @Param("imageId") Integer imageId);
}
