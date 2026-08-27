package io.github.hardikjain3012.summarizeai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;

    @GetMapping("/embedding")
    public float[] embedding() {
        return embeddingModel.embed("Hello from SummarizeAi");
    }

    @PostMapping("/vector")
    public String testVectorStore() {

        Document document = new Document(
                "SummarizeAI is an AI-powered RAG application."
        );

        vectorStore.add(List.of(document));

        return "Document stored successfully";
    }

    @GetMapping("/search")
    public List<Document> search(@RequestParam String query) {
        return vectorStore.similaritySearch(query);
    }
}
