package dev.chipichapa.memestore.dto.meme;

import dev.chipichapa.memestore.domain.model.Meme;

import java.util.List;

public class GetMemeResponse extends Meme {
    public GetMemeResponse(long id, long authorId, long assetId, List<Integer> tagIds, String title, String description) {
        super(id, authorId, assetId, tagIds, title, description);
    }

    public GetMemeResponse(Meme meme){
        super(meme.getId(), meme.getAuthorId(), meme.getAssetId(), meme.getTagIds(), meme.getTitle(), meme.getDescription());
    }

}
