package io.github.hardikjain3012.summarizeai.controller;

import io.github.hardikjain3012.summarizeai.service.ai.ClaudeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class AiTestController {

    private final ClaudeService claudeService;

    @GetMapping("/claude")
    public String testClaude(@RequestParam String question) {

        return claudeService.ask(question);
    }
}