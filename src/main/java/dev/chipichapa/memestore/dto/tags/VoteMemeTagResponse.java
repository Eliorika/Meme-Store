package dev.chipichapa.memestore.dto.tags;

import dev.chipichapa.memestore.domain.model.tag.MemeTag;

import java.util.List;

public record VoteMemeTagResponse(List<MemeTag> tags) {
}
