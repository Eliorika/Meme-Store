package dev.chipichapa.memestore.service;

import dev.chipichapa.memestore.dto.recommedation.RecommendedItems;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationClient {

    private final RestClient filterRestClient;

    @SneakyThrows
    public RecommendedItems getRecommendedItems(int offset, int limit, long userId) {
        ResponseEntity<RecommendedItems> response = filterRestClient.get()
                .uri("/generated", Map.of("userId", userId, "offset", offset, "limit", limit))
                .retrieve()
                .toEntity(RecommendedItems.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info(response.getStatusCode().toString());
            RecommendedItems items = response.getBody();
            log.info(items.toString());
            return items;
        } else {
            log.error(response.getStatusCode().toString());
            log.error("DATA " + offset + limit + userId);
            return null;
        }
    }
}
