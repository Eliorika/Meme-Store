package dev.chipichapa.memestore.exception;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ExceptionBodyResponse {
    private String message;
    private Map<String, String> errors;
    private long creationTime;

    public ExceptionBodyResponse(String message) {
        this.message = message;
        this.creationTime = new Date().getTime();
    }

    public ExceptionBodyResponse(String message, Map<String, String> errors) {
        this.message = message;
        this.errors = errors;
        this.creationTime = new Date().getTime();
    }
}
