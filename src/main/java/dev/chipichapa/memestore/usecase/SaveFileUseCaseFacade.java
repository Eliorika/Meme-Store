package dev.chipichapa.memestore.usecase;

import dev.chipichapa.memestore.dto.file.SaveFileResponse;
import dev.chipichapa.memestore.service.ifc.FileService;
import dev.chipichapa.memestore.usecase.ifc.SaveFileUseCase;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class SaveFileUseCaseFacade implements SaveFileUseCase {

    private final FileService fileService;

    @Override
    @SneakyThrows
    public SaveFileResponse save(MultipartFile file) {
        String uuid = fileService.save(file);
        return new SaveFileResponse(uuid);
    }
}
