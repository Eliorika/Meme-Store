package dev.chipichapa.memestore.usecase.ifc;

import dev.chipichapa.memestore.domain.model.FeedItem;

import java.util.List;

public interface FeedUseCase {
    List<FeedItem> getPublicFeed(int offset, int limit);
    List<FeedItem> getRecommendedFeed(int offset, int limit);
}
