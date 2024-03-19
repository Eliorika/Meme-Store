package dev.chipichapa.memestore.utils.mapper.typesense;

import dev.chipichapa.memestore.typesense.dto.File;
import org.springframework.stereotype.Component;
import org.typesense.model.SearchResult;
import org.typesense.model.SearchResultHit;

import java.util.List;

@Component
public class SearchResultToFileMapper {

    public List<File> map(SearchResult result) {
        return result.getHits()
                .stream()
                .map(SearchResultHit::getDocument)
                .map(document -> new File(
                        Integer.parseInt((String) document.get("id")),
                        (String) document.get("description"),
                        (String) document.get("title"),
                        (String) document.get("ocrText"),
                        (List<String>) document.get("tags")
                )).toList();
    }
}
