package com.tarun.ai_withjava.controller;

import com.tarun.ai_withjava.service.ModelService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Home")
public class Aicontroller {

    @Autowired
    private ChatModel chatModel;

    @Autowired
    private ModelService modelService;
    @GetMapping("/models")
    public Map<String,Object> listOfModels(){
        return Map.of("MODELS",List.of(
                Map.of("Google",List.of("Gemini-flash","Gemini-Thinking","Gemini-Pro")),
                Map.of("OpenAi",List.of("Gpt-5","Gpt-5-Codex","Gpt-5-NANO"))
        ));
    }

    @GetMapping("/test")
    public String test_chat(){
        return "Hi this is a test";
    }

    @PostMapping("/chat")
    public String aiChat(
            @RequestHeader(value = "AI-Provider",defaultValue = "Gemini")
            String provider,
            @RequestHeader(value="AI-Model",defaultValue = "models/gemini-2.0-flash")
            String model,
            @RequestBody String userInput)
    {

        ChatClient chatClient = modelService.getChatClient("Gemini");

            return chatClient.prompt()
                    .user(userInput)
                    .options(GoogleGenAiChatOptions.builder()
                            .model(model)
                            .temperature(0.5)
                            // .maxOutputTokens(500)
                            .build())
                    .call()// give me entire response at one go
                    .content();

    }

}
