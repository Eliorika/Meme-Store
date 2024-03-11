package dev.chipichapa.memestore.service.ifc;

import dev.chipichapa.memestore.domain.entity.Image;
import dev.chipichapa.memestore.domain.entity.Tag;

import java.util.List;

public interface TagService {
    List<Integer> addTagsToImageAndReturnTagsIds(Image image, List<String> tags);

    Tag getById(Long id);
}
