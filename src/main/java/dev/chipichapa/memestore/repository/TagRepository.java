package dev.chipichapa.memestore.repository;

import dev.chipichapa.memestore.domain.entity.Tag;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

    @Query(value = """
              SELECT tag.id
              FROM Image img
              JOIN img.imageTags it
              JOIN it.tag tag
              WHERE img.id = (:id)
            """)
    List<Integer> findTagsIdsByImageId(@Param("id") Integer id);

    @Modifying
    @Query(value = """
            INSERT INTO image_tags AS ai (image_id, tag_id)
            VALUES (:imageId, :tagId)
            """, nativeQuery = true)
    void saveTagForImage(@Param("imageId") Integer imageId, @Param("tagId") Integer tagId);

    Set<Tag> findByTagIn(Collection<String> tags);
}
