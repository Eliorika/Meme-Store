package dev.chipichapa.memestore.service;

import dev.chipichapa.memestore.domain.entity.Album;
import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.dto.gallery.GalleryCreateRequest;
import dev.chipichapa.memestore.exception.ResourceNotFoundException;
import dev.chipichapa.memestore.repository.AlbumRepository;
import dev.chipichapa.memestore.service.ifc.AlbumService;
import dev.chipichapa.memestore.usecase.GalleryUseCaseImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepository;

    @Override
    public Album getGalleryById(int id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMessage = String.format("Gallery with id: %s -- is not found", id);
                    return new ResourceNotFoundException(errorMessage);
                });
    }


    @Override
    public Album saveGallery(GalleryCreateRequest galleryCreateRequest, User user){
        Album album = new Album()
                .setName(galleryCreateRequest.name())
                .setDescription(galleryCreateRequest.description())
                .setAuthor(user);
        return albumRepository.save(album);
    }



}
