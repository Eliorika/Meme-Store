package dev.chipichapa.memestore.repository;

import dev.chipichapa.memestore.domain.entity.Image;
import dev.chipichapa.memestore.domain.entity.ImageTag;
import dev.chipichapa.memestore.domain.entity.ImageTagPK;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageTagRepository extends CrudRepository<ImageTag, ImageTagPK> {
    List<ImageTag> findByImage(Image image);
}
