package dev.chipichapa.memestore.typesense.events;

import dev.chipichapa.memestore.domain.entity.Image;
import dev.chipichapa.memestore.ocr.dto.OcrImage;
import dev.chipichapa.memestore.ocr.service.OcrService;
import dev.chipichapa.memestore.service.ifc.FileService;
import dev.chipichapa.memestore.service.ifc.ImageService;
import dev.chipichapa.memestore.service.ifc.SearchService;
import dev.chipichapa.memestore.typesense.dto.File;
import dev.chipichapa.memestore.typesense.dto.SavedMeme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveMemeEventListener {

    private final SearchService typesense;
    private final ImageService imageService;
    private final FileService fileService;
    private final OcrService ocrService;

    @EventListener
    public void accept(SaveMemeEvent event) {
        SavedMeme savedMeme = event.getSavedMeme();
        typesense.saveOrUpdate(savedMemeToFile(savedMeme));
    }

    public File savedMemeToFile(SavedMeme savedMeme){
        return new File(
                savedMeme.id(),
                savedMeme.description().toLowerCase(),
                savedMeme.title().toLowerCase(),
                getOcrText(savedMeme).toLowerCase(),
                savedMeme.tags()
        );
    }

    private String getOcrText(SavedMeme savedMeme) {
        Image image = imageService.getById(Long.valueOf(savedMeme.id()));
        String filenameWithExtension = image.getFilenameWithExtension();

        dev.chipichapa.memestore.s3storage.dto.File file = fileService.get(filenameWithExtension);

        if(ocrService.isTokenExpireSoon()){
            ocrService.requestAndSaveNewToken();
        }

        String textFromImage = ocrService.extractTextFromImage(new OcrImage(
                file.getContent(),
                image.getExtension()
        ));

        return textFromImage;
    }
}
