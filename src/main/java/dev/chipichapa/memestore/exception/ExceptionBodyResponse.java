package dev.chipichapa.memestore.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ExceptionBodyResponse {

    private boolean error;
    private int code;
    private String message;

    public ExceptionBodyResponse(int code, String message) {
        this.error = true;
        this.code = code;
        this.message = message;
    }
}
