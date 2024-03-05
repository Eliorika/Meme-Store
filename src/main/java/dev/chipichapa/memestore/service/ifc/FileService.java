package dev.chipichapa.memestore.service.ifc;

import dev.chipichapa.memestore.s3storage.dto.File;

public interface FileService {
    void save(File file);

    File get(String filename);
}
