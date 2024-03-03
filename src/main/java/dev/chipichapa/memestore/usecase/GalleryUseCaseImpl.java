package dev.chipichapa.memestore.usecase;

import dev.chipichapa.memestore.domain.entity.Album;
import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.domain.model.Gallery;
import dev.chipichapa.memestore.dto.gallery.GalleryCreateRequest;
import dev.chipichapa.memestore.service.ifc.AlbumService;
import dev.chipichapa.memestore.service.ifc.UserService;
import dev.chipichapa.memestore.usecase.ifc.GalleryUseCase;
import dev.chipichapa.memestore.utils.AuthUtils;
import dev.chipichapa.memestore.utils.mapper.AlbumMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GalleryUseCaseImpl implements GalleryUseCase {

    private final AlbumService albumService;
    private final UserService userService;
    private final AuthUtils authUtils;

    @Override
    public Gallery create(GalleryCreateRequest request) {
        UserDetails userDetails = authUtils.getUserDetailsOrThrow();

        User userEntity = userService.getByUsername(userDetails.getUsername());
        Album album = albumService.saveGallery(request, userEntity);
        return AlbumMapper.map(album);
    }

    @Override
    public Gallery getById(int id) {
        return AlbumMapper.map(albumService.getGalleryById(id));
    }
}
