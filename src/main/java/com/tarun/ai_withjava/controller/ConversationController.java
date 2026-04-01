package com.tarun.ai_withjava.controller;

import com.tarun.ai_withjava.service.ConversationService;
import com.tarun.ai_withjava.service.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/conversation")
public class ConversationController {

    private static final Logger log = LoggerFactory.getLogger(ConversationController.class);


    @Autowired
    private ConversationService conversationService;
    @Qualifier("geminiChatClient")
    @Autowired
    private ChatClient chatClient;

    @PostMapping("/{conversationId}")
    public Map<String, Object> conversation(
            @PathVariable String conversationId,
            @RequestBody String message
    ){
        log.info("===REQUEST RECIVED===");
        log.info("Message : {}",message);
        String provider = "Gemini";
        log.info("====REQUEST RECEIVED====");
        log.info("Provider : {}",provider);
        log.info("Message : {}",message);
        //add user message to conversation history
        conversationService.addUserMessage(conversationId,message);
        //get convo history with token Unit
        List<Message> history  = conversationService.getRecentMessages(conversationId);
        //send message and history to the API call to AI
        var promptSpec = chatClient.prompt().messages(history);
        //AI responses will be saved to conversation hiostory
        ChatResponse response = promptSpec.call().chatResponse();
        String aiResponse = response.getResult().getOutput().toString();

        log.info("aiResponse = " +aiResponse);

        //Response will be shown to user
        conversationService.addAiMessage(conversationId,aiResponse);

        //storge(In memory)
        //conversation id
        //token management  - context window limiy
        return Map.of(
                "conversationId",conversationId,
                "aiResponse",aiResponse,
                "messageCount",conversationService.getConversationInfo(conversationId).get("messageCount"),
                "totalTokens",conversationService.getConversationInfo(conversationId).get("totalTokens")
        );
    }

}
