package dev.chipichapa.memestore.usecase.ifc;


import dev.chipichapa.memestore.domain.enumerated.VoteType;
import dev.chipichapa.memestore.dto.tags.GetMemeTagsResponse;
import dev.chipichapa.memestore.dto.tags.VoteMemeTagResponse;
import org.springframework.lang.Nullable;

public interface MemeTagsUseCase {
    GetMemeTagsResponse getMemeTags(Long memeId, Long galleryId);

    VoteMemeTagResponse voteMemeTag(Long galleryId, Long memeId, Long tagId, @Nullable VoteType type) throws Exception;

}
