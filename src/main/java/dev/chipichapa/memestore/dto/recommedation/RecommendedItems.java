package dev.chipichapa.memestore.dto.recommedation;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RecommendedItems(
        @NotNull List<Long> ids
) {
}
