package dev.chipichapa.memestore.service;

import dev.chipichapa.memestore.config.RabbitMQConfig;
import dev.chipichapa.memestore.dto.recommedation.ItemRabbitDTO;
import dev.chipichapa.memestore.dto.recommedation.MarkRabbitDTO;
import dev.chipichapa.memestore.dto.recommedation.UserRabbitDTO;
import dev.chipichapa.memestore.service.ifc.RecommendationRabbitProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class RecommendationRabbitProducerImpl implements RecommendationRabbitProducer {

    private final RabbitTemplate rabbitTemplate;

    private final RabbitMQConfig rabbitMQConfig;

    @Override
    public void sendItem(ItemRabbitDTO dto) {
        send(dto);
    }

    @Override
    public void sendUser(UserRabbitDTO dto) {
        send(dto);
    }

    @Override
    public void removeItem(ItemRabbitDTO dto) {
        send(dto);
    }

    @Override
    public void removeUser(UserRabbitDTO dto) {
        send(dto);
    }

    @Override
    public void sendMark(MarkRabbitDTO dto) {
        send(dto);
    }

    private void send(Object data) {
        try {
            rabbitTemplate.convertAndSend(rabbitMQConfig.exchange(), rabbitMQConfig.queue(), data);
            log.info("Rabbit request add data - " + data);
        } catch (Exception e) {
           log.error(data.toString() + " --- " +  e.getMessage());
        }
    }
}
