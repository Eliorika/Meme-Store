package dev.chipichapa.memestore.ocr.repository;

import dev.chipichapa.memestore.ocr.entity.OcrToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OcrTokenRepository extends CrudRepository<OcrToken, Long> {
}
