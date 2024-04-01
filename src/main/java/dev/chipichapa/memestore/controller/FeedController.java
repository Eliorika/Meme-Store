package dev.chipichapa.memestore.controller;

import dev.chipichapa.memestore.domain.dto.BasicApiResponse;
import dev.chipichapa.memestore.domain.model.FeedItem;
import dev.chipichapa.memestore.usecase.ifc.FeedUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/feed")
public class FeedController {

    private final FeedUseCase feedUseCase;

    @GetMapping("/public")
    public ResponseEntity<BasicApiResponse<List<FeedItem>>> getPublicFeed(@RequestParam int offset, @RequestParam int limit) {
        return ResponseEntity.ok(new BasicApiResponse<>(feedUseCase.getPublicFeed(offset, limit)));
    }

    @GetMapping("/recommended")
    public ResponseEntity<BasicApiResponse<List<FeedItem>>> getRecommendedFeed(@RequestParam int offset, @RequestParam int limit) {
        return ResponseEntity.ok(new BasicApiResponse<>(feedUseCase.getRecommendedFeed(offset, limit)));
    }

    @GetMapping("/gallery/{id}")
    public ResponseEntity<BasicApiResponse<List<FeedItem>>> getGalleryFeed(@PathVariable("id") int id, @RequestParam int offset, @RequestParam int limit) {
        return ResponseEntity.ok(new BasicApiResponse<>(feedUseCase.getGalleryFeed(id, offset, limit)));
    }
}
