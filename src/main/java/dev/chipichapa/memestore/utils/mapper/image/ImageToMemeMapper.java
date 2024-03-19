package dev.chipichapa.memestore.utils.mapper.image;

import dev.chipichapa.memestore.domain.entity.Image;
import dev.chipichapa.memestore.domain.model.Meme;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImageToMemeMapper {

    public Meme toMeme(Image image, List<Integer> imageTagsIds){
       return new Meme(image.getId(),
                image.getAuthor().getId(),
                image.getId(),
                imageTagsIds,
                image.getTitle(),
                image.getDescription());
    }

}
