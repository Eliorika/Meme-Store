package dev.chipichapa.memestore.repository;

import dev.chipichapa.memestore.domain.entity.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageTagRepository extends CrudRepository<Tag, Integer> {

    @Query(value = """
              SELECT tag.id
              FROM Image img
              JOIN img.imageTags it
              JOIN it.tag tag
              WHERE img.id = (:id)
            """)
    List<Integer> findByImageId(@Param("id") Integer id);
}
