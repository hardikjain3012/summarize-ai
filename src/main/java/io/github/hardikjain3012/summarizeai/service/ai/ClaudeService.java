package io.github.hardikjain3012.summarizeai.service.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClaudeService {

    private final ChatClient claudeChatClient;

    public String ask(String question) {

        return claudeChatClient
                .prompt()
                .user(question)
                .call()
                .content();
    }
}