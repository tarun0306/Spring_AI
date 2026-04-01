package com.tarun.ai_withjava.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
/*This is a service that we use to switch between providers
* but then i am yet present not implementing this functionality*/
public class ModelService {

    @Autowired
    @Qualifier("geminiChatClient")
    private ChatClient geminiChatClient;

    public ChatClient getChatClient(String provider){

        return geminiChatClient;
    }
}

