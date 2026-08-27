package io.github.hardikjain3012.summarizeai.config;

import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClaudeConfig {

    @Bean
    public ChatClient claudeChatClient(AnthropicChatModel chatModel) {
        return ChatClient.builder(chatModel).build();
    }
}