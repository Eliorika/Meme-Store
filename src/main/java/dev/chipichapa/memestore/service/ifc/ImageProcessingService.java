package dev.chipichapa.memestore.service.ifc;

import java.util.List;

public interface ImageProcessingService {

    List<String> predictTags(byte[] image);
}
