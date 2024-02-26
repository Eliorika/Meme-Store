package dev.chipichapa.memestore.domain.model.tag;

import lombok.Getter;

@Getter
public class MemeTag extends Tag{

    private final long memeId;
    private final int score;
    private final int vote;

    public MemeTag(long id, String name, long memeId, int score, int vote) {
        super(id, name);
        this.memeId = memeId;
        this.score = score;
        this.vote = vote;
    }
}
