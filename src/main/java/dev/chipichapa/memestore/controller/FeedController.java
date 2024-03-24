package dev.chipichapa.memestore.controller;

import dev.chipichapa.memestore.domain.model.FeedItem;
import dev.chipichapa.memestore.usecase.ifc.FeedUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/feed")
public class FeedController {

    private final FeedUseCase feedUseCase;

    @GetMapping("/public")
    public ResponseEntity<List<FeedItem>> getPublicFeed(@RequestParam int offset, @RequestParam int limit) {
        return ResponseEntity.ok(feedUseCase.getPublicFeed(offset, limit));
    }

    @GetMapping("/recommended")
    public ResponseEntity<List<FeedItem>> getRecommendedFeed(@RequestParam int offset, @RequestParam int limit) {
        return ResponseEntity.ok(feedUseCase.getRecommendedFeed(offset, limit));
    }
}
