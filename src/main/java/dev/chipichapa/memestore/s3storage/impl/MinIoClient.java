package dev.chipichapa.memestore.s3storage.impl;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import dev.chipichapa.memestore.s3storage.S3Client;
import dev.chipichapa.memestore.s3storage.dto.File;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class MinIoClient implements S3Client {

    private final AmazonS3 s3;

    public void createBucketIfNotExist(String bucket) {
        if (!s3.doesBucketExistV2(bucket)) {
            s3.createBucket(bucket);
        }
    }

    @Override
    @SneakyThrows
    public void put(File file, String bucket) {
        byte[] content = file.getContent();
        @Cleanup InputStream inputStream = new ByteArrayInputStream(content);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(content.length);

        s3.putObject(
                new PutObjectRequest(bucket, file.getFilename(), inputStream, metadata)
        );
    }

    @Override
    @SneakyThrows
    public byte[] get(File file, String bucket) {
        S3Object s3Object = s3.getObject(
                new GetObjectRequest(bucket, file.getFilename())
        );
        return s3Object.getObjectContent().readAllBytes();
    }

    @Override
    public boolean isFileExist(File file, String bucket) {
        return s3.doesObjectExist(bucket, file.getFilename());
    }

    @Override
    public void delete(File file, String bucket) {
        s3.deleteObject(new DeleteObjectRequest(bucket, file.getFilename()));
    }
}
