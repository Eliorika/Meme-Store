package dev.chipichapa.memestore.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BasicApiResponse <T> {
    private boolean error;
    private List<T> result;

    public BasicApiResponse(boolean error, T result) {
        this.error = error;
        this.result = List.of(result);
    }
}
