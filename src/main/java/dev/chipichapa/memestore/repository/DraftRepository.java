package dev.chipichapa.memestore.repository;

import dev.chipichapa.memestore.domain.entity.Draft;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DraftRepository extends CrudRepository<Draft, UUID> {
}
