package io.github.hardikjain3012.summarizeai.service.document;

import io.github.hardikjain3012.summarizeai.entity.DocumentEntity;
import io.github.hardikjain3012.summarizeai.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final VectorStore vectorStore;
    private final DocumentRepository documentRepository;

    private final TokenTextSplitter splitter = TokenTextSplitter.builder()
            .withChunkSize(800)
            .withMinChunkSizeChars(350)
            .withMinChunkLengthToEmbed(5)
            .withMaxNumChunks(10_000)
            .build();

    public UUID process(MultipartFile file) {

        try {
            // 1. Save parent document
            DocumentEntity documentEntity = DocumentEntity.builder()
                    .filename(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .uploadedAt(LocalDateTime.now())
                    .build();

            documentEntity = documentRepository.save(documentEntity);

            // 2. Read content
            String content = new String(
                    file.getBytes(),
                    StandardCharsets.UTF_8
            );

            // 3. Create original Spring AI document
            Document document = new Document(
                    content,
                    Map.of(
                            "documentId", documentEntity.getId().toString(),
                            "filename", file.getOriginalFilename()
                    )
            );

            // 4. Chunk document
            List<Document> chunks = splitter.apply(List.of(document));

            // 5. Add chunk number to every chunk
            List<Document> enrichedChunks = new ArrayList<>();

            for (int i = 0; i < chunks.size(); i++) {

                Document chunk = chunks.get(i);

                Map<String, Object> metadata =
                        new HashMap<>(chunk.getMetadata());

                metadata.put("documentId",
                        documentEntity.getId().toString());

                metadata.put("filename",
                        file.getOriginalFilename());

                metadata.put("chunkNumber", i + 1);

                enrichedChunks.add(
                        new Document(chunk.getText(), metadata)
                );
            }

            // 6. Generate embeddings + store chunks
            vectorStore.add(enrichedChunks);

            return documentEntity.getId();

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to process uploaded document", e
            );
        }
    }
}