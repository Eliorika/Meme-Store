package dev.chipichapa.memestore.domain.entity;

import java.io.Serializable;

public class ImageTagPK implements Serializable {

    protected Image image;
    protected Tag tag;

    public ImageTagPK() {}

    public ImageTagPK(Image image, Tag tag) {
        this.image = image;
        this.tag = tag;
    }
}
