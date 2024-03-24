package dev.chipichapa.memestore.usecase.ifc;

import dev.chipichapa.memestore.dto.meme.SearchMemeResponse;

import java.util.List;

public interface SearchUseCase {

    SearchMemeResponse search(String query);
}
