package dev.chipichapa.memestore.service;

import dev.chipichapa.memestore.s3storage.S3Client;
import dev.chipichapa.memestore.s3storage.config.S3Properties;
import dev.chipichapa.memestore.s3storage.dto.File;
import dev.chipichapa.memestore.service.ifc.FileService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

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
    public void save(File file) {
        minioClient.put(file, bucket);
    }

    @Override
    public File get(String filename) {
        byte[] bytes = minioClient.get(new File(filename), bucket);

        return new File(filename, bytes);
    }
}
