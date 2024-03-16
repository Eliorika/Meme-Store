package dev.chipichapa.memestore.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedItem {
    @JsonProperty("gallery_id")
    private long galleryId;

    @JsonProperty("meme_id")
    private long memeId;
}
