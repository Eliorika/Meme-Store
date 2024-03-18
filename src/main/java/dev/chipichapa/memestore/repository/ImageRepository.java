package dev.chipichapa.memestore.repository;

import dev.chipichapa.memestore.domain.entity.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByUuid(UUID uuid);

    @Modifying
    @Query(value = """
            INSERT INTO album_images AS ai (image_id, album_id) 
            VALUES (:imageId, :albumId)
            """, nativeQuery = true)
    void saveImage(@Param("albumId") Integer albumId, @Param("imageId") Integer imageId);

    @Query(value = """
        SELECT image
        FROM Image image
        JOIN image.albums album
        WHERE album.visible = true
        """,
        countQuery = """
        SELECT count(image)
        FROM Image image
        JOIN image.albums album
        WHERE album.visible = true
        """
    )
    Page<Image> findAllVisibleLastImages(Pageable page);

    @Query(value = """
        SELECT image
        FROM Image image
        JOIN image.albums album
        WHERE image.id = :imageId AND album.visible = true
""")
    Optional<Image> findImageIfVisibleAlbumsExist(@Param("imageId") long imageId);
}
