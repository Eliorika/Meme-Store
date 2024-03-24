package dev.chipichapa.memestore.dto.meme;

import dev.chipichapa.memestore.domain.model.Meme;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchMemeResponse{
    public SearchMemeResponse(List<Meme> memes) {
        this.memes = memes;
    }

    private List<Meme> memes;
}
