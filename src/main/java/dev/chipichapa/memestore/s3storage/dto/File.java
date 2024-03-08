package dev.chipichapa.memestore.s3storage.dto;

import lombok.Getter;

@Getter
public class File {
    private String filename;
    private byte[] content;

    public File(String filename, byte[] content) {
        this.filename = filename;
        this.content = content;
    }

    public File(String filename) {
        this.filename = filename;
    }
}
