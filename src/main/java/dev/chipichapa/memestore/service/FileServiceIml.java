package dev.chipichapa.memestore.service;

import dev.chipichapa.memestore.client.S3Client;
import dev.chipichapa.memestore.service.ifc.FileService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceIml implements FileService {

    private final S3Client minioClient;

    @Override
    @SneakyThrows
    public String save(MultipartFile file) {
        String filename = file.getOriginalFilename();
        UUID uuid = UUID.randomUUID();

        if (FilenameUtils.isExtension(filename, "png", "gif", "bmp", "jpg")) {
            filename = uuid + ".jpg";
        }
        minioClient.save(file.getBytes(), filename);

        return uuid.toString();
    }
}
