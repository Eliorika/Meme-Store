package dev.chipichapa.memestore.repository;

import dev.chipichapa.memestore.domain.entity.Album;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

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

    @Query(value = """
            select count (*) > 0 from albums a, album_contributors ac 
            where (a.author_id = :userId or (ac.user_id = :userId and ac.album_id = :albumId))
            """, nativeQuery = true)
    boolean isOwnerOrContributor(@Param("userId") Long userId, @Param("albumId") Integer albumId);

    @Query(value = """
            select *
            from albums
            where (author_id = :author and type = 'BIN')
            """, nativeQuery = true)
    Optional<Album> findBinByUser(@Param("author")int author);

    List<Album> findByContributorsId(long authorId);
}
