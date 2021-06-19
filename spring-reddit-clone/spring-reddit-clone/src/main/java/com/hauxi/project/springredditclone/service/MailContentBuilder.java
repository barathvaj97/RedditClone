package com.hauxi.project.springredditclone.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import lombok.AllArgsConstructor;


//ThymeLeaf Mail generation

@Service
@AllArgsConstructor
class MailContentBuilder {
    private final TemplateEngine templateEngine;
    
    String build(String message){
        Context context = new Context();
        context.setVariable("message", message);

        return templateEngine.process("mailTemplate", context);
    }
}
