package dev.chipichapa.memestore.service.ifc;

import dev.chipichapa.memestore.domain.entity.Image;

public interface ImageService {
    Image getById(Long id);

    Image getByTicket(String ticket);
}
