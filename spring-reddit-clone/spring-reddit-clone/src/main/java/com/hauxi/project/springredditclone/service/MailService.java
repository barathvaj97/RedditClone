package com.hauxi.project.springredditclone.service;

import com.hauxi.project.springredditclone.exception.RedditException;
import com.hauxi.project.springredditclone.model.NotificationEmail;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
    
    private final JavaMailSender mailSender;

    @Async
    void sendMail(NotificationEmail notificationEmail) throws RedditException{
        MimeMessagePreparator messagePreparator = mimeMessage ->{
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("mayankparihartest@email.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(notificationEmail.getBody());
        };

        try{
            mailSender.send(messagePreparator);
           log.info("Activation Email sent !!");
        }

        catch (MailException e){
            log.error("Exception occured on sending mail", e);
            throw new RedditException("Exception occured on sending mail" + notificationEmail.getRecipient(),e);
        }   
    }
    
}
