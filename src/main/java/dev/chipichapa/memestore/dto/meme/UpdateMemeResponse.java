package dev.chipichapa.memestore.dto.meme;

import dev.chipichapa.memestore.domain.model.Meme;

import java.util.List;

public class UpdateMemeResponse extends Meme {
    public UpdateMemeResponse(long id, long authorId, long assetId, List<Integer> tagIds, String title, String description) {
        super(id, authorId, assetId, tagIds, title, description);
    }

    public UpdateMemeResponse(Meme meme){
        super(meme.getId(), meme.getAuthorId(), meme.getAssetId(), meme.getTagIds(), meme.getTitle(), meme.getDescription());
    }
}
