package dev.chipichapa.memestore.controller;

import dev.chipichapa.memestore.domain.dto.BasicApiResponse;
import dev.chipichapa.memestore.dto.meme.*;
import dev.chipichapa.memestore.dto.tags.GetMemeTagsResponse;
import dev.chipichapa.memestore.dto.tags.VoteMemeTagRequest;
import dev.chipichapa.memestore.dto.tags.VoteMemeTagResponse;
import dev.chipichapa.memestore.usecase.ifc.MemeTagsUseCase;
import dev.chipichapa.memestore.usecase.ifc.MemeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/meme")
@RequiredArgsConstructor
public class MemeController {

    private final MemeUseCase memeUseCase;
    private final MemeTagsUseCase memeTagsUseCase;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<BasicApiResponse<?>> create(@RequestParam("asset") String assetTicket,
                                               @RequestParam("gallery_id") Integer galleryId,
                                               @RequestBody CreateMemeRequest createMemeRequest) {

        CreateMemeResponse response = memeUseCase.create(
                new CreateMemeRequest(createMemeRequest, galleryId, assetTicket)
        );

        return new ResponseEntity<>(new BasicApiResponse<>(false, response), HttpStatus.OK);
    }

    @GetMapping("{gallery_id}_{id}")
    @PreAuthorize("permitAll()")
    ResponseEntity<BasicApiResponse<?>> get(@PathVariable("gallery_id") long galleryId,
                                            @PathVariable("id") long memeId) {

        GetMemeResponse response = memeUseCase.get(new GetMemeRequest(galleryId, memeId));
        return new ResponseEntity<>(new BasicApiResponse<>(false, response), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<BasicApiResponse<?>> update(@PathVariable("id") long memeId,
                                               @RequestBody UpdateMemeRequest updateRequest) {

        UpdateMemeResponse response = memeUseCase.update(updateRequest, memeId);
        return new ResponseEntity<>(new BasicApiResponse<>(false, response), HttpStatus.OK);
    }


    @GetMapping("{gallery_id}_{id}/tags")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<BasicApiResponse<?>> getMemeTags(@PathVariable("gallery_id") Long galleryId,
                                                    @PathVariable("id") Long memeId){

        GetMemeTagsResponse response = memeTagsUseCase.getMemeTags(memeId, galleryId);
        return new ResponseEntity<>(new BasicApiResponse<>(false, response.tags()), HttpStatus.OK);
    }

    @PostMapping("{id}/vote/{tag_id}")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<BasicApiResponse<?>> getMemeTags(@PathVariable("id") Long memeId,
                                                    @PathVariable("tag_id") Long tagId,
                                                    @RequestBody VoteMemeTagRequest request){

        VoteMemeTagResponse response = memeTagsUseCase.voteMemeTag(memeId, tagId, request.type());
        return new ResponseEntity<>(new BasicApiResponse<>(false, response), HttpStatus.OK);
    }


}
