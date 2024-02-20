package dev.chipichapa.memestore.client.impl;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import dev.chipichapa.memestore.client.S3Client;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class MinioClient implements S3Client {

    private final AmazonS3 client;
    private final static String DEFAULT_BUCKET = "meme-store-bucket";

    @PostConstruct
    private void createDefaultBucketIfNotExist() {
        if (!client.doesBucketExistV2(DEFAULT_BUCKET)) {
            client.createBucket(DEFAULT_BUCKET);
        }
    }

    @Override
    @SneakyThrows
    public void save(byte[] file, String filename) {
        InputStream inputStream = new ByteArrayInputStream(file);
        PutObjectResult putObjectResult = client.putObject(
                new PutObjectRequest(DEFAULT_BUCKET, filename, inputStream, new ObjectMetadata())
        );
        inputStream.close();

    }

    @Override
    public byte[] get(String fileId) {
        return new byte[0];
    }

}
