package com.tarun.ai_withjava.service;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class CodeReviewService {

    private final ResourceLoader resourceLoader;

    public CodeReviewService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Prompt createCodeReviewPrompt(String code, String language, String businessReq){
        //loadtemplate
        String templateContent = loadTemplate();

        PromptTemplate promptTemplate = new PromptTemplate(templateContent);

        Map<String,Object> variables = Map.of(
                "code",code,
                "language",language,
                "businessRequirements",businessReq
        );

        return  promptTemplate.create(variables);
    }

    private String loadTemplate(){
        try{
            Resource resource = resourceLoader.getResource(
                    "classpath:templates/code-review.txt"
            );
            return resource.getContentAsString(StandardCharsets.UTF_8);
        }
        catch(IOException e){
            throw new RuntimeException("Failed to Read the Template " ,e);
        }


    }

}
