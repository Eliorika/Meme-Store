package dev.chipichapa.memestore.service;

import dev.chipichapa.memestore.domain.entity.Album;
import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.domain.enumerated.AlbumType;
import dev.chipichapa.memestore.dto.gallery.GalleryCreateRequest;
import dev.chipichapa.memestore.exception.ResourceNotFoundException;
import dev.chipichapa.memestore.repository.AlbumRepository;
import dev.chipichapa.memestore.service.ifc.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                .setAuthor(user)
                .setVisible(galleryCreateRequest.isPublic());
        return albumRepository.save(album);
    }

    @Override
    @Transactional
    public Album saveChangesGallery(GalleryCreateRequest galleryChanges, int id) {
        Album album = getGalleryById(id);
        album
                .setName(galleryChanges.name())
                .setDescription(galleryChanges.description())
                .setVisible(galleryChanges.isPublic());
        return albumRepository.save(album);
    }

    @Override
    public void addDefaultsGalleriesForUser(User user) {
        Album defaultPublicAlbum = new Album()
                .setAlbumType(AlbumType.DEFAULT)
                .setVisible(true)
                .setName("Мой открытый альбом")
                .setAuthor(user);

        Album defaultPrivateAlbum = new Album()
                .setAlbumType(AlbumType.DEFAULT)
                .setVisible(false)
                .setName("Мой закрытый альбом")
                .setAuthor(user);

        Album defaultBinAlbum = new Album()
                .setAlbumType(AlbumType.BIN)
                .setVisible(false)
                .setName("Корзина пользователя")
                .setAuthor(user);

        albumRepository.saveAll(List.of(defaultPublicAlbum, defaultPrivateAlbum, defaultBinAlbum));
    }

    @Override
    public void deleteGallery(int id) {
        albumRepository.deleteById(id);
    }

    @Override
    public List<Album> getAllByAuthor(long authorId){
        return albumRepository.findAllByAuthorId(authorId);
    }



}
