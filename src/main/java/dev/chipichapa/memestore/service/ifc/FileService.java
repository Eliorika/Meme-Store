package dev.chipichapa.memestore.service.ifc;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String save(MultipartFile file);
}
