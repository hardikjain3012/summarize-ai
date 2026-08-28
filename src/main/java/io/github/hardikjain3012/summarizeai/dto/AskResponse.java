package io.github.hardikjain3012.summarizeai.dto;

import java.util.List;
import java.util.UUID;

public record AskResponse(String answer, List<Source> sources) {
    public record Source(
            UUID documentId,
            String filename
    ) {}
}