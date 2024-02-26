package dev.chipichapa.memestore.service;

import dev.chipichapa.memestore.s3storage.S3Client;
import dev.chipichapa.memestore.s3storage.config.S3Properties;
import dev.chipichapa.memestore.s3storage.dto.File;
import dev.chipichapa.memestore.service.ifc.FileService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final S3Client minioClient;
    private final S3Properties s3Properties;

    private String bucket;

    @PostConstruct
    public void init(){
        bucket = s3Properties.getBucket();
        minioClient.createBucketIfNotExist(bucket);
    }

    @Override
    @SneakyThrows
    public String save(MultipartFile file) {
        String filename = file.getOriginalFilename();
        UUID uuid = UUID.randomUUID();

        if (FilenameUtils.isExtension(filename, "png", "gif", "bmp", "jpg")) {
            filename = uuid + ".jpg";
        }

        minioClient.put(new File(filename, file.getBytes()), bucket);
        return uuid.toString();
    }
}
