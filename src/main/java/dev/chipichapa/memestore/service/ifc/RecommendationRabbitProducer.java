package dev.chipichapa.memestore.service.ifc;

import dev.chipichapa.memestore.dto.recommedation.ItemRabbitDTO;
import dev.chipichapa.memestore.dto.recommedation.MarkRabbitDTO;
import dev.chipichapa.memestore.dto.recommedation.UserRabbitDTO;

public interface RecommendationRabbitProducer {
    void sendItem(ItemRabbitDTO dto);

    void sendUser(UserRabbitDTO dto);

    void sendMark(MarkRabbitDTO dto);

    void removeItem(ItemRabbitDTO dto);

    void removeUser(UserRabbitDTO dto);
}
