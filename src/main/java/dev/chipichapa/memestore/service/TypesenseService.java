package dev.chipichapa.memestore.service;

import dev.chipichapa.memestore.exception.TypesenseException;
import dev.chipichapa.memestore.service.ifc.SearchService;
import dev.chipichapa.memestore.typesense.dto.File;
import dev.chipichapa.memestore.utils.mapper.typesense.FileDocumentMapper;
import dev.chipichapa.memestore.utils.mapper.typesense.SearchResultToFileMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.typesense.api.Client;
import org.typesense.api.Collections;
import org.typesense.api.Documents;
import org.typesense.api.FieldTypes;
import org.typesense.model.CollectionSchema;
import org.typesense.model.Field;
import org.typesense.model.SearchParameters;
import org.typesense.model.SearchResult;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TypesenseService implements SearchService {

    private final Client typesenseClient;

    private final FileDocumentMapper fileDocumentMapper;
    private final SearchResultToFileMapper searchResultToFileMapper;

    @Value("${typesense.collection-name}")
    private String TYPESENSE_COLLECTION_NAME;

    @PostConstruct
    private void init() {
        try {
            createSchema();
        } catch (Exception ignored) {
        }
    }

    @Override
    public void saveOrUpdate(File file) {
        Map<String, Object> document = fileDocumentMapper.toDocument(file);
        Documents request = typesenseClient.collections(TYPESENSE_COLLECTION_NAME).documents();

        try {
            request.upsert(document);
        } catch (Exception e) {
            throw new TypesenseException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            typesenseClient.collections(TYPESENSE_COLLECTION_NAME).documents(String.valueOf(id)).delete();
        } catch (Exception e) {
            throw new TypesenseException(e);
        }
    }

    @Override
    public File get(Integer id) {
        Map<String, Object> document;
        try {
            document = typesenseClient.collections(TYPESENSE_COLLECTION_NAME).documents(String.valueOf(id)).retrieve();
        } catch (Exception e) {
            throw new TypesenseException(e);
        }

        return fileDocumentMapper.toFile(document);
    }

    @Override
    public List<File> getFilesByQuery(String query) {
        SearchParameters searchParameters = new SearchParameters()
                .q(query.toLowerCase())
                .queryBy("title,description,ocrText,tags");
        Documents request = typesenseClient.collections(TYPESENSE_COLLECTION_NAME).documents();

        SearchResult result;
        try {
            result = request.search(searchParameters);
        } catch (Exception e) {
            throw new TypesenseException(e);
        }

        return searchResultToFileMapper.map(result);
    }

    private void createSchema() {
        CollectionSchema collectionSchema = new CollectionSchema();

        collectionSchema.name(TYPESENSE_COLLECTION_NAME)
                .addFieldsItem(new Field().name("title").type(FieldTypes.STRING).optional(true))
                .addFieldsItem(new Field().name("description").type(FieldTypes.STRING).optional(true))
                .addFieldsItem(new Field().name("ocrText").type(FieldTypes.STRING).optional(true))
                .addFieldsItem(new Field().name("tags").type(FieldTypes.STRING_ARRAY).optional(true));

        Collections request = typesenseClient.collections();

        try {
            request.create(collectionSchema);
        } catch (Exception e) {
            throw new TypesenseException(e);
        }
    }
}
