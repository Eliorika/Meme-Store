package dev.chipichapa.memestore.dto.gallery;

import lombok.Data;

@Data
public class GalleryRequest {
    Integer id=null;
    String name;
    String description;
    boolean isPublic;

}
