package com.tarun.ai_withjava.controller;

import com.tarun.ai_withjava.service.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/stream")
@CrossOrigin(origins = "*")
public class StreamChat {

    private static final Logger log = LoggerFactory.getLogger(StreamChat.class);
    private final ChatClient chatClient;

    public StreamChat(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }
    //flux is part of project reactor
    //flux is a stream of Zero or more items
    //stream of text chunck
    //text/event-stream will tell Spring
    //this is a SSE endpoiny(Server Sent Events)
    //Handle streaming protocol automatically
    //strem?provider =gemini&model=dasda&message=count
    @GetMapping(value="/streams",produces = "text/event-stream")
    public Flux<String> chat(
            @RequestParam(required = false) String model,
            @RequestParam String message
    ){
        String provider = "Gemini";
        log.info("====REQUEST RECEIVED====");
        log.info("Provider : {}",provider);
        log.info("Model Header : {}",model);
        log.info("Message : {}",message);



        log.info("ChatCLient class : {}",chatClient.getClass().getName());
        log.info("ChatClient : {} ", chatClient);


        return chatClient.prompt()
                .user(message)
                .options(GoogleGenAiChatOptions.builder()
                        .model(model)
                        .temperature(0.5)
                        .build())
                .stream()
                .content();
    }
}
