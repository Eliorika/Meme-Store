package dev.chipichapa.memestore.service;

import dev.chipichapa.memestore.domain.entity.Image;
import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.dto.recommedation.ItemRabbitDTO;
import dev.chipichapa.memestore.dto.recommedation.UserRabbitDTO;
import dev.chipichapa.memestore.repository.ImageRepository;
import dev.chipichapa.memestore.repository.UserRepository;
import dev.chipichapa.memestore.service.ifc.RecommendationRabbitProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableAsync
public class RecommendationSyncScheduler {
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final RecommendationRabbitProducer recommendationRabbitProducer;

    @Async
    @Scheduled(fixedDelay = 100000000L, initialDelay = 10000L)
    public void updateRecommendations() {
        List<Image> images = imageRepository.findAll();
        List<User> users = userRepository.findAll();

        try {
            for (Image image : images) {
                recommendationRabbitProducer.sendItem(new ItemRabbitDTO(image.getId()));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        try {
            for (User user : users) {
                recommendationRabbitProducer.sendUser(new UserRabbitDTO(user.getId()));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info("Finish synchronizing the database with the recommendation service.");
    }
}
