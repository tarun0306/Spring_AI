package com.tarun.ai_withjava.config;

import com.google.api.client.util.Value;
import com.google.genai.Chat;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.model.google.genai.autoconfigure.chat.GoogleGenAiChatProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MultiModelCoNFIG {


    @Value("${openai.api.key}")
    private String openaiKey;
    @Value("${openai.api.model.name}")
    private String openAIModelName;
    @Value("${openai.api.completions.path}")
    private String completionPath;
    @Value("${openai.api.url}")
    private String openAiUrl;

    @Primary
    @Bean("geminiChatClient")
    public ChatClient geminiChatClient(GoogleGenAiChatModel geminiGenAiChat){
        ChatClient chatClient = ChatClient.create(geminiGenAiChat);
        return chatClient;
    }
    @Bean("openaiChatClient")
    public ChatClient openAIChatClient(GoogleGenAiChatModel openAiChatModel){
        return null;
    }


}
