package dev.chipichapa.memestore.service;

import dev.chipichapa.memestore.domain.entity.Album;
import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.domain.enumerated.AlbumType;
import dev.chipichapa.memestore.dto.gallery.GalleryCreateRequest;
import dev.chipichapa.memestore.exception.ResourceNotFoundException;
import dev.chipichapa.memestore.repository.AlbumRepository;
import dev.chipichapa.memestore.repository.ImageRepository;
import dev.chipichapa.memestore.service.ifc.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepository;
    private final ImageRepository imageRepository;

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
                .setVisible(galleryCreateRequest.isPublic())
                .setAlbumType(AlbumType.USER_CREATED);
        return albumRepository.save(album);
    }

    @Override
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

    @Override
    public boolean isVisibleAlbum(long albumId) {
        return albumRepository.findByIdAndVisibleTrue((int) albumId);
    }

    @Override
    public Set<Long> getAllContributorIdsIncludeOwner(long albumId) {

        Album album = albumRepository.findById((int) albumId).orElseThrow(
                () -> new ResourceNotFoundException("Album with id = %d is not found"
                        .formatted(albumId))
        );

        Set<Long> contributors = album.getContributors()
                .stream()
                .map(User::getId)
                .collect(Collectors.toSet());
        contributors.add(album.getAuthor().getId());

        return contributors;
    }

    @Override
    public Album getBin(int userId) {

        Album res = albumRepository.findBinByUser(userId).orElseThrow(
                () -> new ResourceNotFoundException("Album Bin for user with id = %d is not found"
                        .formatted(userId)));
        return res;
    }


    @Override
    public void deleteFromBin(int userId, long memeId) {
        Album bin = albumRepository.findBinByUser(userId).orElseThrow(
                () -> new ResourceNotFoundException("Album Bin for user with id = %d is not found"
                        .formatted(userId)));

        var img = imageRepository.findById(memeId).orElseThrow(
                () -> new ResourceNotFoundException("Image with id = %d is not found"
                        .formatted(memeId)));

        bin.getImages().remove(img);
        albumRepository.save(bin);
    }

    @Override
    public void moveTo(long imageId, long from, long to) {
        var image = imageRepository.findById(imageId).orElseThrow(() -> {
            String message = "Not found Image with %s id";
            return new ResourceNotFoundException(String.format(message, imageId));
        });
        var albumFrom = albumRepository.findById((int) from).orElseThrow(() -> {
            String message = "Not found Album with %s id";
            return new ResourceNotFoundException(String.format(message, from));
        });
        var albumTo = albumRepository.findById((int) to).orElseThrow(() -> {
            String message = "Not found Album with %s id";
            return new ResourceNotFoundException(String.format(message, to));
        });

        albumFrom.getImages().remove(image);
        albumTo.getImages().add(image);


        albumRepository.save(albumFrom);
        albumRepository.save(albumTo);
    }


}
