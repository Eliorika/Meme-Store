package dev.chipichapa.memestore.controller;

import dev.chipichapa.memestore.domain.entity.Album;
import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.domain.model.Gallery;
import dev.chipichapa.memestore.dto.gallery.GalleryCreateRequest;
import dev.chipichapa.memestore.service.ifc.AlbumService;
import dev.chipichapa.memestore.usecase.ifc.GalleryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gallery")
@RequiredArgsConstructor
public class GalleryController {
    private final GalleryUseCase galleryUseCase;


    @PostMapping("/create")
    public ResponseEntity<Gallery> create(@RequestBody GalleryCreateRequest galleryCreateRequest){
        Gallery gallery = galleryUseCase.create(galleryCreateRequest);
        return ResponseEntity.ok(gallery);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gallery> getGalleryById(@PathVariable int id){
        Gallery gallery = galleryUseCase.getById(id);
        return ResponseEntity.ok(gallery);
    }

    //@PostMapping("/update")
    //public void GallaryUpdate(Integer id, String name, String description, boolean isPublic){
      /*  Optional<Album> thisGallery = repo.findById(id);
        thisGallery.name = name;
        thisGallery.description = description;
        thisGallery.isPublic = isPublic;
        repo.save(thisGallery)
    }*/

    //@PostMapping("/delete");

}
