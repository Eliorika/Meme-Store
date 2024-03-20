package dev.chipichapa.memestore.usecase;

import dev.chipichapa.memestore.domain.entity.Album;
import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.domain.model.Gallery;
import dev.chipichapa.memestore.dto.gallery.ContributorsGallery;
import dev.chipichapa.memestore.dto.gallery.GalleryCreateRequest;
import dev.chipichapa.memestore.service.ifc.AlbumService;
import dev.chipichapa.memestore.service.ifc.UserService;
import dev.chipichapa.memestore.usecase.ifc.GalleryUseCase;
import dev.chipichapa.memestore.utils.AuthUtils;
import dev.chipichapa.memestore.utils.mapper.AlbumMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


import java.util.List;

@Service
@RequiredArgsConstructor
public class GalleryUseCaseImpl implements GalleryUseCase {

    private final AlbumService albumService;
    private final UserService userService;
    private final AuthUtils authUtils;

    @Override
    @Transactional
    public Gallery create(GalleryCreateRequest request) {
        UserDetails userDetails = authUtils.getUserDetailsOrThrow();

        User userEntity = userService.getByUsername(userDetails.getUsername());
        Album album = albumService.saveGallery(request, userEntity);
        return AlbumMapper.map(album);
    }

    @Override
    @Transactional
    public Gallery getById(int id) {
        return AlbumMapper.map(albumService.getGalleryById(id));
    }

    @Override
    @Transactional
    public Gallery saveGalleryChanges(GalleryCreateRequest galleryChanges, int id) {
        return AlbumMapper.map(albumService.saveChangesGallery(galleryChanges, id));
    }

    @Override
    @Transactional
    public boolean deleteGallery(int id) {
        try {
            albumService.deleteGallery(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public List<Gallery> getAllForUser(User user) {
        var res = albumService.getAllByAuthor(user.getId()).stream().map(al-> AlbumMapper.map(al)).collect(Collectors.toList());
        return res;
    }


    @Override
    @Transactional
    public Gallery addContributors(int id, ContributorsGallery contributorsGallery) {
        Album album = albumService.getGalleryById(id);
        Set<User> contributors = album.getContributors();
        for (long userId : contributorsGallery.contributorsIds()) {
            User user = userService.getById(userId);
            contributors.add(user);
        }
        return AlbumMapper.map(album);
    }
    @Override
    @Transactional
    public Gallery deleteContributors(int id, ContributorsGallery contributorsGallery) {
        Album album = albumService.getGalleryById(id);
        Set<User> contributors = album.getContributors();
        contributors = contributors.stream()
                .filter(user -> !contributorsGallery.contributorsIds().contains(user.getId()))
                .collect(Collectors.toSet());
        album.setContributors(contributors);
        return AlbumMapper.map(album);
    }

    public List<Gallery> getAll() {
        UserDetails userDetails = authUtils.getUserDetailsOrThrow();

        User user = userService.getByUsername(userDetails.getUsername());
        List<Album> galleryList = albumService.getAllByAuthor(user.getId());
        return AlbumMapper.map(galleryList);
    }



}
