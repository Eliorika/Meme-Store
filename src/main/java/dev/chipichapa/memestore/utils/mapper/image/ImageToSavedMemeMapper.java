package dev.chipichapa.memestore.utils.mapper.image;

import dev.chipichapa.memestore.domain.entity.Image;
import dev.chipichapa.memestore.domain.entity.ImageTag;
import dev.chipichapa.memestore.domain.entity.Tag;
import dev.chipichapa.memestore.typesense.dto.SavedMeme;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImageToSavedMemeMapper {

    public SavedMeme to(Image image){
        return new SavedMeme(
                image.getId(),
                image.getTitle(),
                image.getDescription(),
                getTags(image.getImageTags()));
    }

    private List<String> getTags(List<ImageTag> tags){
        return tags
                .stream()
                .map(ImageTag::getTag)
                .map(Tag::getTag)
                .toList();
    }
}
