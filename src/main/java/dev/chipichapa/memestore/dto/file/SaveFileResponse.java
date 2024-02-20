package dev.chipichapa.memestore.dto.file;

import java.util.List;

public record SaveFileResponse(String uuid, List<String> potentialTags) {
}
