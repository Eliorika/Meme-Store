package dev.chipichapa.memestore.usecase;

import dev.chipichapa.memestore.domain.entity.Album;
import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.domain.model.Gallery;
import dev.chipichapa.memestore.dto.gallery.GalleryCreateRequest;
import dev.chipichapa.memestore.service.ifc.AlbumService;
import dev.chipichapa.memestore.usecase.ifc.GalleryUseCase;
import dev.chipichapa.memestore.utils.mapper.AlbumMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GalleryUseCaseImpl implements GalleryUseCase {

    private final AlbumService albumService;



    @Override
    public Gallery create(GalleryCreateRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Album album = albumService.saveGallery(request, user);
        return AlbumMapper.map(album);
    }

    @Override
    public Gallery getById(int id) {
        return AlbumMapper.map(albumService.getGalleryById(id));
    }
}
