package io.github.hardikjain3012.summarizeai.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final VectorStore vectorStore;

    public void process(MultipartFile file) {

        try {
            String content = new String(file.getBytes());

            Document document = new Document(content);

            vectorStore.add(List.of(document));

        } catch (IOException e) {
            throw new RuntimeException("Failed to read uploaded file", e);
        }
    }
}