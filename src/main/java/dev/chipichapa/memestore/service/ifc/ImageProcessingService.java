package dev.chipichapa.memestore.service.ifc;

import java.util.Set;

public interface ImageProcessingService {

    Set<String> predictTags(byte[] image);
}
