package dev.chipichapa.memestore.exception;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ExceptionBodyResponse {

    private int code;
    private String message;
    private long creationTime;

    public ExceptionBodyResponse(int code, String message) {
        this.code = code;
        this.message = message;
        this.creationTime = new Date().getTime();
    }
}
