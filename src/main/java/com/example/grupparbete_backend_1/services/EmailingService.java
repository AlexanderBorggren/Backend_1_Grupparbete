package com.example.grupparbete_backend_1.services;

import com.example.grupparbete_backend_1.dto.MailRequestDto;
import com.example.grupparbete_backend_1.models.EmailingTemplates;
import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface EmailingService {


    EmailingTemplates getTemplateWithTemplateName(String templateName);

    EmailingTemplates getTemplate(Long id);

    List<EmailingTemplates> getAllTemplates();

    EmailingTemplates createTemplate(String templateName,String description, String subject, String body);

    EmailingTemplates updateTemplate(Long id, String newTemplateName, String newTemplateDescription, String newSubject, String newBody);


    @Async
    void sendEmail(MailRequestDto request, String customerName, Long room, String roomType, String startDate, String endDate, int guestQuantity, int extraBedsQuantity) throws MessagingException;

    @Async
    void sendEmail(MailRequestDto request, String userName) throws MessagingException;

    @Async
    void sendConfirmationEmail(MailRequestDto request) throws MessagingException;

    String deleteTemplate(Long templateId);
}
