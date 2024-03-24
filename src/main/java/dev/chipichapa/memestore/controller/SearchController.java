package dev.chipichapa.memestore.controller;

import dev.chipichapa.memestore.dto.meme.SearchMemeResponse;
import dev.chipichapa.memestore.service.ifc.SearchService;
import dev.chipichapa.memestore.typesense.dto.File;
import dev.chipichapa.memestore.usecase.ifc.SearchUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService typesenseService;
    private final SearchUseCase searchUseCase;

    @GetMapping("/find")
    ResponseEntity<?> find(@RequestParam("query") String query){
        SearchMemeResponse searchMemeResponse = searchUseCase.search(query);
        return ResponseEntity.ok(searchMemeResponse);
    }

    @GetMapping("/get")
    ResponseEntity<?> get(@RequestParam("id") Integer id){
        File file = typesenseService.get(id);
        return ResponseEntity.ok(file);
    }
}
