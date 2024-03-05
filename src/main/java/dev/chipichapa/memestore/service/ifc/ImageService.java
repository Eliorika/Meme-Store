package dev.chipichapa.memestore.service.ifc;

import dev.chipichapa.memestore.domain.entity.Image;

public interface ImageService {

    Image getByTemporaryTicket(String ticket);
}
