package io.github.hardikjain3012.summarizeai.dto;

import java.util.List;

public record RagResult(String answer, List<AskResponse.Source> sources) {
}
