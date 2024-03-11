package dev.chipichapa.memestore.utils.mapper;

import dev.chipichapa.memestore.domain.entity.ImageTag;
import dev.chipichapa.memestore.domain.model.tag.MemeTag;
import dev.chipichapa.memestore.dto.tags.GetMemeTagsResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ImageTagsToGetMemeTagsResponseMapper {

    public GetMemeTagsResponse toResponse(List<ImageTag> tags) {
        List<MemeTag> result = new ArrayList<>(tags.size());

        for (ImageTag imageTag : tags) {
            MemeTag tag = new MemeTag(
                    imageTag.getTag().getId(),
                    imageTag.getTag().getTag(),
                    imageTag.getImage().getId(),
                    imageTag.getScore(),
                    0);
            result.add(tag);
        }

        return new GetMemeTagsResponse(result);
    }
}
