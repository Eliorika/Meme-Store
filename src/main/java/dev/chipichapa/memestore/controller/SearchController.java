package dev.chipichapa.memestore.controller;

import dev.chipichapa.memestore.service.ifc.SearchService;
import dev.chipichapa.memestore.typesense.dto.File;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService typesenseService;

    @GetMapping("/find")
    ResponseEntity<?> find(@RequestParam("query") String query){
        List<File> files = typesenseService.getFilesByQuery(query);

        return ResponseEntity.ok(files);
    }

    @GetMapping("/get")
    ResponseEntity<?> get(@RequestParam("id") Integer id){
        File file = typesenseService.get(id);
        return ResponseEntity.ok(file);
    }
}
