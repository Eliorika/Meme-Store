package dev.chipichapa.memestore.usecase;

import dev.chipichapa.memestore.domain.entity.Image;
import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.domain.model.FeedItem;
import dev.chipichapa.memestore.dto.recommedation.RecommendedItems;
import dev.chipichapa.memestore.service.RecommendationClient;
import dev.chipichapa.memestore.service.ifc.ImageService;
import dev.chipichapa.memestore.usecase.ifc.FeedUseCase;
import dev.chipichapa.memestore.utils.AuthUtils;
import dev.chipichapa.memestore.utils.mapper.image.ImageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedUseCaseImpl implements FeedUseCase {

    private final ImageService imageService;

    private final RecommendationClient client;

    private final AuthUtils authUtils;

    @Override
    @Transactional
    public List<FeedItem> getPublicFeed(int offset, int limit) {
        // TODO: check images albums - all must have visible = true
        List<Image> images = imageService.getLastPublicImages(offset, limit);
        List<FeedItem> feedItems = new ArrayList<>();

        for (var image : images) {
            // the album with the most memes is used
            var albums = new ArrayList<>(image.getAlbums());
            albums.sort(Comparator.comparingInt(o -> o.getImages().size()));
            feedItems.add(ImageMapper.toFeedItem(image, albums.getFirst()));
        }

        return feedItems;
    }

    @Override
    @Transactional
    public List<FeedItem> getRecommendedFeed(int offset, int limit) {
        User user = authUtils.getUserEntity();
        RecommendedItems recommendedItems;

        try {
            recommendedItems = client.getRecommendedItems(offset, limit, user.getId());
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
            return getPublicFeed(offset, limit);
        }
        List<FeedItem> feedItems = new ArrayList<>();

        for (var imageId : recommendedItems.ids()) {
            // the album with the most memes is used
            Optional<Image> optionalImage = imageService.getImageIfPublicAlbumsExists(imageId);
            if (optionalImage.isPresent()) {
                Image image = optionalImage.get();
                var albums = new ArrayList<>(image.getAlbums());
                albums.sort(Comparator.comparingInt(o -> o.getImages().size()));
                feedItems.add(ImageMapper.toFeedItem(image, albums.getFirst()));
            }
        }

        return feedItems;
    }
}
