package com.example.grupparbete_backend_1.services;

import com.example.grupparbete_backend_1.dto.MailRequestDto;
import com.example.grupparbete_backend_1.models.EmailingTemplates;
import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;

public interface EmailingService {

    String getTemplate(String templateName);

    EmailingTemplates createTemplate(String templateName, String subject, String body);

    @Async
    void sendEmail(MailRequestDto request) throws MessagingException;


}
