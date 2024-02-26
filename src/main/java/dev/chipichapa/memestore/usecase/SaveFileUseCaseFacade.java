package dev.chipichapa.memestore.usecase;

import dev.chipichapa.memestore.dto.file.SaveFileResponse;
import dev.chipichapa.memestore.service.ifc.FileService;
import dev.chipichapa.memestore.service.ifc.ImageProcessingService;
import dev.chipichapa.memestore.usecase.ifc.SaveFileUseCase;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SaveFileUseCaseFacade implements SaveFileUseCase {

    private final FileService fileService;
    private final ImageProcessingService imageProcessingService;

    @Override
    @SneakyThrows
    public SaveFileResponse save(MultipartFile file) {
        String uuid = fileService.save(file);
        List<String> tags = imageProcessingService.predictTags(file.getBytes());

        return new SaveFileResponse(uuid, tags);
    }
}
