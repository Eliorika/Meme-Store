package dev.chipichapa.memestore.dto.tags;

import dev.chipichapa.memestore.domain.enumerated.VoteType;
import org.springframework.lang.Nullable;

public record GetMemeTagsRequest(@Nullable VoteType type) {
}