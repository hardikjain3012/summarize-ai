package io.github.hardikjain3012.summarizeai.service.ai;

import io.github.hardikjain3012.summarizeai.dto.AskResponse;
import io.github.hardikjain3012.summarizeai.dto.RagResult;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RagService {

    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    public RagResult ask(String question) {

        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(question)
                        .topK(3)
                        .similarityThreshold(0.7)
                        .build()
        );

        if (documents.isEmpty()) {
            return new RagResult(
                    "I don't have enough information to answer that based on the provided documents.",
                    List.of()
            );
        }

        documents.forEach(document ->
                System.out.println("Metadata: " + document.getMetadata())
        );

        String context = documents.stream()
                .map(Document::getText)
                .reduce("", (a, b) -> a + "\n\n" + b);

        String answer = chatClient.prompt()
                .system("""
                You are a helpful assistant answering questions
                based only on the provided documents.

                Do not use your own knowledge.

                If the answer cannot be found in the provided context,
                say that you don't have enough information.
                """)
                .user("""
                Context:
                %s

                Question:
                %s
                """.formatted(context, question))
                .call()
                .content();

        List<AskResponse.Source> sources = documents.stream()
                .map(document -> {
                    UUID documentId = UUID.fromString(
                            document.getMetadata()
                                    .get("parent_document_id")
                                    .toString()
                    );

                    return new AskResponse.Source(
                            documentId,
                            document.getMetadata()
                                    .get("filename")
                                    .toString()
                    );
                })
                .distinct()
                .toList();

        return new RagResult(answer, sources);
    }
}