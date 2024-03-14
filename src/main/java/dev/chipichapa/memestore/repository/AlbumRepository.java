package dev.chipichapa.memestore.repository;

import dev.chipichapa.memestore.domain.entity.Album;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlbumRepository extends CrudRepository<Album, Integer> {

    List<Album> findAllByAuthorId(long authorId);

    @Query(value = """
            select count(*) > 0 from album_images where (image_id = :imageId and album_id = :albumId)
            """, nativeQuery = true)
    boolean existsImageById(@Param("albumId") Long albumId, @Param("imageId") Long imageId);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Album a " +
            "WHERE a.id = :id AND a.visible = TRUE")
    boolean findByIdAndVisibleTrue(@Param("id") Integer id);
}
