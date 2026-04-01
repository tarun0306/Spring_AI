package com.tarun.ai_withjava.service;

import com.tarun.ai_withjava.model.ConversationHistory;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConversationService {



    //in memory storage
    private final Map<String, ConversationHistory> conversations = new ConcurrentHashMap<>();

    //context
    private static final int DEFAULT_TOKEN_LIMIT = 4000;


    public ConversationHistory getConversation(String conversaytionId) {
        return conversations.computeIfAbsent(conversaytionId,id->new ConversationHistory(id));
    }

    public void addUserMessage(String conversationId, String content) {
        ConversationHistory history = getConversation(conversationId);
        history.addMessage(new UserMessage(content));
    }
    public void addAiMessage(String conversationId, String content){
        ConversationHistory history = getConversation(conversationId);
        history.addMessage(new AssistantMessage(content));
    }

    public List<Message> getMessage(String conversationid){
        ConversationHistory history = getConversation(conversationid);
        return history.getMessages();
    }
    public List<Message> getRecentMessages(String conversationId,int maxTokens){
        ConversationHistory history = getConversation(conversationId);
        return  history.getRecentMessages(maxTokens);
    }
    public List<Message> getRecentMessages(String conversationId){
        return getRecentMessages(conversationId,DEFAULT_TOKEN_LIMIT);
    }

    public Map<String,Object> getConversationInfo(String conversationId){
        ConversationHistory history = conversations.get(conversationId);

        if(history==null){
            return Map.of("exists",false);
        }
        return Map.of(
                "exists",true,
                "conversationId",history.getConversationId(),
                "messageCount",history.getMessageCount(conversationId),
                "totalTokens",history.getTotalTokens(),
                "createdAt",history.getCreatedAt().toString(),
                "updatedAt",history.getUpdatedAt().toString()
        );
    }

    public List<String> listConsversations(){
        return conversations.keySet().stream().toList();
    }
}
