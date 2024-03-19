package dev.chipichapa.memestore.service.ifc;

import dev.chipichapa.memestore.typesense.dto.File;

import java.util.List;

public interface SearchService {

    void saveOrUpdate(File file);

    void delete(Integer id);

    File get(Integer id);

    List<File> getFilesByQuery(String query);

}
