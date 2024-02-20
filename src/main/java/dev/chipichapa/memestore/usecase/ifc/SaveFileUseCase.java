package dev.chipichapa.memestore.usecase.ifc;

import dev.chipichapa.memestore.dto.file.SaveFileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface SaveFileUseCase {
    SaveFileResponse save(MultipartFile file);
}
