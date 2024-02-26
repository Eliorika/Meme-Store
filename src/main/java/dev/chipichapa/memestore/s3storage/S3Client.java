package dev.chipichapa.memestore.s3storage;

import dev.chipichapa.memestore.s3storage.dto.File;

public interface S3Client {
    void put(File file, String bucket);

    byte[] get(File file, String bucket);

    boolean isFileExist(File file, String bucket);

    void delete(File file, String bucket);

    void createBucketIfNotExist(String bucket);

}
