package dev.chipichapa.memestore.utils.mapper.typesense;

import dev.chipichapa.memestore.typesense.dto.File;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FileDocumentMapper {

    public Map<String, Object> toDocument(File file) {
        Map<String, Object> document = new HashMap<>();

        document.put("id", file.id());
        document.put("title", file.title());
        document.put("description", file.description());
        document.put("ocrText", file.ocrText());
        document.put("tags", file.tags());

        return document;
    }

    public File toFile(Map<String, Object> document) {
        return new File(
                Integer.parseInt((String) document.get("id")),
                (String) document.get("description"),
                (String) document.get("title"),
                (String) document.get("ocrText"),
                (List<String>) document.get("tags")
        );
    }
}
