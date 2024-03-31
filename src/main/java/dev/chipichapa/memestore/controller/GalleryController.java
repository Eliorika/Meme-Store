package dev.chipichapa.memestore.controller;

import dev.chipichapa.memestore.domain.dto.BasicApiResponse;
import dev.chipichapa.memestore.domain.model.Gallery;
import dev.chipichapa.memestore.dto.AvailableName;
import dev.chipichapa.memestore.dto.gallery.ContributorsGallery;
import dev.chipichapa.memestore.dto.gallery.GalleryCreateRequest;
import dev.chipichapa.memestore.usecase.ifc.GalleryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gallery")
@RequiredArgsConstructor
public class GalleryController {
    private final GalleryUseCase galleryUseCase;


    @PostMapping("/create")
    public ResponseEntity<BasicApiResponse<Gallery>> create(@RequestBody GalleryCreateRequest galleryCreateRequest) {
        Gallery gallery = galleryUseCase.create(galleryCreateRequest);
        return ResponseEntity.ok(new BasicApiResponse<>(gallery));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasicApiResponse<Gallery>> getGalleryById(@PathVariable int id) {
        Gallery gallery = galleryUseCase.getById(id);
        return ResponseEntity.ok(new BasicApiResponse<>(gallery));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasicApiResponse<Gallery>> saveGalleryChanges(@PathVariable int id, @RequestBody GalleryCreateRequest galleryCreateRequest) {
        Gallery gallery = galleryUseCase.saveGalleryChanges(galleryCreateRequest, id);
        return ResponseEntity.ok(new BasicApiResponse<>(gallery));
    }

    @PostMapping("/{id}/contributors/add")
    public ResponseEntity<BasicApiResponse<Gallery>> add(@PathVariable int id, @RequestBody ContributorsGallery contributorsGallery) {
        Gallery gallery = galleryUseCase.addContributors(id, contributorsGallery);
        return ResponseEntity.ok(new BasicApiResponse<>(gallery));
    }

    @PostMapping("/{id}/contributors/remove")
    public ResponseEntity<BasicApiResponse<Gallery>> delete(@PathVariable int id, @RequestBody ContributorsGallery contributorsGallery) {
        Gallery gallery = galleryUseCase.deleteContributors(id, contributorsGallery);
        return ResponseEntity.ok(new BasicApiResponse<>(gallery));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<BasicApiResponse<Boolean>> deleteGallery(@PathVariable int id) {
        return ResponseEntity.ok(new BasicApiResponse<>(galleryUseCase.deleteGallery(id)));
    }

    @GetMapping("/")
    public ResponseEntity<BasicApiResponse<List<Gallery>>> getAll() {
        return ResponseEntity.ok(new BasicApiResponse<>(galleryUseCase.getAll()));
    }

    @GetMapping("/available_names")
    public ResponseEntity<BasicApiResponse<List<AvailableName>>> getAvailableMemes() {
        List<AvailableName> availableNames = galleryUseCase.getAvailableMemes();
        return new ResponseEntity<>(new BasicApiResponse<>(availableNames), HttpStatus.OK);
    }

}
