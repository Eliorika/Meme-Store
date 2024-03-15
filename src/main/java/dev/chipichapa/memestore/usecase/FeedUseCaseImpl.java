package dev.chipichapa.memestore.usecase;

import dev.chipichapa.memestore.domain.model.FeedItem;
import dev.chipichapa.memestore.usecase.ifc.FeedUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedUseCaseImpl implements FeedUseCase {
    @Override
    @Transactional
    public List<FeedItem> getPublicFeed(int offset, int limit) {
        return null;
    }

    @Override
    @Transactional
    public List<FeedItem> getRecommendedFeed(int offset, int limit) {
        return null;
    }
}
