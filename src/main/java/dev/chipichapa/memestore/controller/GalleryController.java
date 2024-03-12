package dev.chipichapa.memestore.controller;

import dev.chipichapa.memestore.domain.model.Gallery;
import dev.chipichapa.memestore.dto.gallery.ContributorsGallery;
import dev.chipichapa.memestore.dto.gallery.GalleryCreateRequest;
import dev.chipichapa.memestore.usecase.ifc.GalleryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/{id}")
    public ResponseEntity<Gallery> saveGalleryChanges(@PathVariable int id, @RequestBody GalleryCreateRequest galleryCreateRequest){
        Gallery gallery = galleryUseCase.saveGalleryChanges(galleryCreateRequest, id);
        return ResponseEntity.ok(gallery);
    }
    @PostMapping("/{id}/contributors/add")
    public ResponseEntity<Gallery> add(@PathVariable int id, @RequestBody ContributorsGallery contributorsGallery){
        Gallery gallery = galleryUseCase.addContributors(id, contributorsGallery);
        return ResponseEntity.ok(gallery);
    }
    @PostMapping("/{id}/contributors/remove")
    public ResponseEntity<Gallery> delete(@PathVariable int id,@RequestBody ContributorsGallery contributorsGallery){
        Gallery gallery = galleryUseCase.deleteContributors(id, contributorsGallery);
        return ResponseEntity.ok(gallery);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteGallery(@PathVariable int id){
        return ResponseEntity.ok(galleryUseCase.deleteGallery(id));
    }
    @GetMapping("/")
    public ResponseEntity<List<Gallery>> getAll(){
        return ResponseEntity.ok(galleryUseCase.getAll());
    }

}
