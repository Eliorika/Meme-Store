package dev.chipichapa.memestore.dto.meme;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateMemeRequest {

    private String title;
    private String description;
    private List<String> tags;
    private int galleryId;
    private String assetTicket;

    public CreateMemeRequest(String title, String description, List<String> tags) {
        this.title = title;
        this.description = description;
        this.tags = tags;
    }

    public CreateMemeRequest(CreateMemeRequest request, int galleryId, String assetTicket){
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.tags = request.getTags();
        this.galleryId = galleryId;
        this.assetTicket = assetTicket;
    }
}
