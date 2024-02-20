package dev.chipichapa.memestore.controller;

import dev.chipichapa.memestore.dto.file.SaveFileResponse;
import dev.chipichapa.memestore.usecase.ifc.SaveFileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class FileController {

    private final SaveFileUseCase saveFileUseCase;

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SaveFileResponse> save(@RequestPart("file") MultipartFile file) {
        SaveFileResponse response = saveFileUseCase.save(file);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
