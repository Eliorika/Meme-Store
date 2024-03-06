package dev.chipichapa.memestore.service.ifc;

import dev.chipichapa.memestore.domain.entity.Image;

import java.util.List;

public interface TagService {
    List<Integer> addTagsToImageAndReturnTagsIds(Image image, List<String> tags);
}
