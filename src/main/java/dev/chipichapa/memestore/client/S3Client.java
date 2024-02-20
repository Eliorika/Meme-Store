package dev.chipichapa.memestore.client;

public interface S3Client {
    void save(byte [] file, String filename);

    byte[] get(String fileId);
}
