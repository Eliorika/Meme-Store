package dev.chipichapa.memestore.utils.mapper;

import dev.chipichapa.memestore.domain.entity.Image;
import dev.chipichapa.memestore.dto.meme.CreateMemeResponse;

import java.util.List;

public class ImageToCreateMemeResponseMapper {

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
}
