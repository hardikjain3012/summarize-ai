package io.github.hardikjain3012.summarizeai.controller;

import io.github.hardikjain3012.summarizeai.service.document.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {

        documentService.process(file);

        return ResponseEntity.ok("Document uploaded successfully");
    }
}