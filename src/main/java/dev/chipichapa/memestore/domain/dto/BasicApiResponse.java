package dev.chipichapa.memestore.domain.dto;

import lombok.Data;

@Data
public class BasicApiResponse <T> {
    private boolean error = false;
    private T result;

    public BasicApiResponse(boolean error, T result) {
        this.error = error;
        this.result = result;
    }
    public BasicApiResponse(T result) {
        this.result = result;
    }
}
