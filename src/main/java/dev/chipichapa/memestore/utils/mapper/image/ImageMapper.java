package dev.chipichapa.memestore.utils.mapper.image;

import dev.chipichapa.memestore.domain.entity.Album;
import dev.chipichapa.memestore.domain.entity.Image;
import dev.chipichapa.memestore.domain.model.FeedItem;
import dev.chipichapa.memestore.dto.meme.CreateMemeResponse;

import java.util.List;

public class ImageMapper {

    public static CreateMemeResponse toResponse(Image image, List<Integer> imageTagIds) {

        return new CreateMemeResponse(
                image.getId(),
                image.getAuthor().getId(),
                image.getId(),
                imageTagIds,
                image.getTitle(),
                image.getDescription()
        );
    }


    public static FeedItem toFeedItem(Image image, Album album) {
        return new FeedItem(album.getId(), image.getId());
    }
}
