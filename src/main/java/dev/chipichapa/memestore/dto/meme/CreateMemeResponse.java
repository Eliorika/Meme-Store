package dev.chipichapa.memestore.dto.meme;

import dev.chipichapa.memestore.domain.model.Meme;

import java.util.List;

public class CreateMemeResponse extends Meme {
    public CreateMemeResponse(long id, long authorId, long assetId, List<Integer> tagIds, String title, String description) {
        super(id, authorId, assetId, tagIds, title, description);
    }
}
