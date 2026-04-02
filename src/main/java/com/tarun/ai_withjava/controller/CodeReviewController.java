package com.tarun.ai_withjava.controller;

import com.google.genai.types.Model;
import com.tarun.ai_withjava.model.CodeReviewerModel;
import com.tarun.ai_withjava.service.CodeReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
// as of now we are not used Prompttemplate

//User==> LLM ==> Showing Response to user

//This product is sepecifi to code Reviewer

//do techincal analysis

//analyse if BR are met or not

//security analysis

//give me response in this format
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/review")
public class CodeReviewController {

    private final static Logger log = LoggerFactory.getLogger(CodeReviewController.class);

    private final ChatClient chatClient;

    private CodeReviewerModel codemodel;

    @Autowired
    private CodeReviewService codeReviewService;

    public CodeReviewController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    @PostMapping("/code-review")
    public Map<String,Object>codeReviewer(
            @RequestBody CodeReviewerModel codeModel
            ){

            String provider = "Gemini";
           String  model = "models/gemini-2.5-flash";
            log.info("====REQUEST RECEIVED====");
            log.info("Provider : {}",provider);
            log.info("Model Header : {}",model);
            log.info("Code : {}",codeModel.getCode());
            log.info("Language : {}",codeModel.getLanguage());
            log.info("businessReq : {}",codeModel.getBusinessReq());

            Prompt prompt = codeReviewService.createCodeReviewPrompt(codeModel.getCode(),codeModel.getLanguage(),codeModel.getBusinessReq());

            log.info("prompt : {}",prompt.getContents());
            String review = chatClient.prompt()
                    .user(prompt.getContents())
                    .options(GoogleGenAiChatOptions.builder()
                            .model(model)
                            .build()).call().content();
            System.out.println("Review : " + review);
        return Map.of(
                "Success" ,true,
                "language" ,codeModel.getLanguage(),
                "provider" , provider,
                "model",model,
                "review",review
                );
        }

    }




