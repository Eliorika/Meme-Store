package dev.chipichapa.memestore.typesense.events;

import dev.chipichapa.memestore.ocr.service.OcrService;
import dev.chipichapa.memestore.service.ifc.FileService;
import dev.chipichapa.memestore.service.ifc.ImageService;
import dev.chipichapa.memestore.service.ifc.SearchService;
import dev.chipichapa.memestore.typesense.dto.File;
import dev.chipichapa.memestore.typesense.dto.SavedMeme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveMemeEventListener {

    private final SearchService typesense;

    @EventListener
    public void accept(SaveMemeEvent event) {
        SavedMeme savedMeme = event.getSavedMeme();
        typesense.saveOrUpdate(savedMemeToFile(savedMeme));
    }

    public File savedMemeToFile(SavedMeme savedMeme) {
        return new File(
                savedMeme.id(),
                savedMeme.description().toLowerCase(),
                savedMeme.title().toLowerCase(),
                "ocr",
                savedMeme.tags()
        );
    }
}
