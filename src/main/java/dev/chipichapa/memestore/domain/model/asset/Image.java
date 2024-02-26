package dev.chipichapa.memestore.domain.model.asset;

import dev.chipichapa.memestore.domain.enumerated.AssertType;
import lombok.Getter;

@Getter
public class Image extends Asset {
    private final String uri;

    public Image(long id, String blurhash, AssertType type, String uri) {
        super(id, blurhash, type);
        this.uri = uri;
    }
}
