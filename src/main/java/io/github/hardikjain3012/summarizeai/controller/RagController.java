package io.github.hardikjain3012.summarizeai.controller;

import io.github.hardikjain3012.summarizeai.dto.AskRequest;
import io.github.hardikjain3012.summarizeai.dto.AskResponse;
import io.github.hardikjain3012.summarizeai.dto.RagResult;
import io.github.hardikjain3012.summarizeai.service.ai.RagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RagController {

    private final RagService ragService;

    @PostMapping("/ask")
    public AskResponse ask(@RequestBody AskRequest request) {

        RagResult result = ragService.ask(request.question());

        return new AskResponse(
                result.answer(),
                result.sources()
        );
    }
}