package io.github.hardikjain3012.summarizeai.repository;

import io.github.hardikjain3012.summarizeai.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentRepository extends JpaRepository<DocumentEntity, UUID> {
}