package com.tarun.ai_withjava.model;

import org.springframework.ai.chat.messages.Message;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConversationHistory {

    private String conversationId;

    private List<Message> messages;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private int totalTokens;

    public ConversationHistory(String conversationId) {
        this.conversationId = conversationId;
        this.messages = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.totalTokens = 0;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getTotalTokens() {
        return totalTokens;
    }

    public void setTotalTokens(int totalTokens) {
        this.totalTokens = totalTokens;
    }

    public void addMessage(Message message){
        this.messages.add(message);
        this.updatedAt = LocalDateTime.now();
        //Simple token estimation : ~4 characters per Token

        this.totalTokens += message.getText().length()/4;
    }
    public List<Message> getAllMessage(){
        return new ArrayList<>(messages);//Return a Copy
    }
    public int getMessageCount(String conversationId){
        return messages.size();
    }
    //sliding window
    public List<Message> getRecentMessages(int maxTokens){

        List<Message> recentMessages = new ArrayList<>();
        int currentTokens = 0;

        //Iterabte from the newest to oldest

        for (int i = messages.size()-1;i>=0;i--){
            Message msg = messages.get(i);
            int msgTokens = msg.getText().length()/4;

            if(currentTokens + msgTokens >maxTokens){
                break;
            }
            recentMessages.add(0,msg);//Add at the beginning
            currentTokens+=msgTokens;
        }
    return recentMessages;
    }

}

