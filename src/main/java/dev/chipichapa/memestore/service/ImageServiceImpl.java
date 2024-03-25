package dev.chipichapa.memestore.service;

import dev.chipichapa.memestore.domain.entity.Image;
import dev.chipichapa.memestore.exception.ResourceNotFoundException;
import dev.chipichapa.memestore.repository.AlbumRepository;
import dev.chipichapa.memestore.repository.ImageRepository;
import dev.chipichapa.memestore.service.ifc.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final AlbumRepository albumRepository;

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

    @Override
    public List<Image> getLastPublicImages(int offset, int limit) {
        return imageRepository.findAllVisibleLastImages(PageRequest.of(offset, limit)).toList();
    }

    @Override
    public Optional<Image> getImageIfPublicAlbumsExists(long imageId) {
        return imageRepository.findImageIfVisibleAlbumsExist(imageId);
    }

    private UUID ticketToUuid(String ticket) {
        return UUID.fromString(ticket);
    }


}
