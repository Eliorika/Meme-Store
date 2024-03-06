package dev.chipichapa.memestore.service;

import dev.chipichapa.memestore.domain.entity.Image;
import dev.chipichapa.memestore.exception.ResourceNotFoundException;
import dev.chipichapa.memestore.repository.ImageRepository;
import dev.chipichapa.memestore.service.ifc.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public Image getById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> {
                    String message = "Not found Image with id=(%d)";
                    return new ResourceNotFoundException(String.format(message, id));
                });
    }

    @Override
    public Image getByTicket(String ticket) {
        UUID uuid = ticketToUuid(ticket);

        return imageRepository.findByUuid(uuid)
                .orElseThrow(() -> {
                    String message = "Not found Image with %s ticket";
                    return new ResourceNotFoundException(String.format(message, ticket));
                });
    }

    private UUID ticketToUuid(String ticket) {
        return UUID.fromString(ticket);
    }
}
