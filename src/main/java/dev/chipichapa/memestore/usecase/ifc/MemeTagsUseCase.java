package dev.chipichapa.memestore.usecase.ifc;


import dev.chipichapa.memestore.domain.entity.ImageTag;
import dev.chipichapa.memestore.domain.entity.Tag;
import dev.chipichapa.memestore.domain.enumerated.VoteType;
import dev.chipichapa.memestore.dto.tags.GetMemeTagsResponse;
import dev.chipichapa.memestore.dto.tags.VoteMemeTagResponse;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MemeTagsUseCase {
    GetMemeTagsResponse getMemeTags(Long memeId, Long galleryId);

    @Transactional
    GetMemeTagsResponse getMemeTagsByMemeOnly(Long memeId);

    VoteMemeTagResponse voteMemeTag(Long galleryId, Long memeId, Long tagId, @Nullable VoteType type) throws Exception;

    List<Tag> getTagList(List<ImageTag> imageTags);
}
