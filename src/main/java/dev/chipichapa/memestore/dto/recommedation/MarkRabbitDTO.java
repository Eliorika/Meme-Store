package dev.chipichapa.memestore.dto.recommedation;

public record MarkRabbitDTO(
        long userId,
        long itemId,
        double mark
) {
}
