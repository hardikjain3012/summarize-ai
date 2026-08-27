package io.github.hardikjain3012.summarizeai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/query")
@RequiredArgsConstructor
public class QueryController {

    private final VectorStore vectorStore;

    @GetMapping
    public List<Document> query(
            @RequestParam String question
    ) {
        return vectorStore.similaritySearch(SearchRequest.builder()
                .query(question)
                .topK(3)
                .build()
        );
    }
}