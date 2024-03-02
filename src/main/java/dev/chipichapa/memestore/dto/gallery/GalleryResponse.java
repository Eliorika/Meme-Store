package dev.chipichapa.memestore.dto.gallery;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GalleryResponse {
    Integer id;
    String name;
    String description;
    boolean isPublic;
}
