package dev.chipichapa.memestore.domain.model.tag;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public class MemeTag extends Tag{

    private final long memeId;
    private final int score;

    @Nullable
    @JsonProperty("my_vote")
    private final Integer vote;

    public MemeTag(long id, String name, long memeId, int score, @Nullable Integer vote) {
        super(id, name);
        this.memeId = memeId;
        this.score = score;
        this.vote = vote;
    }
}
