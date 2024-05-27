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

    EmailingTemplates createTemplate(String templateName,String description, String subject, String body,String fromEmail);

    EmailingTemplates updateTemplate(Long id, String newTemplateName, String newTemplateDescription, String newSubject, String newBody, String newFromEmail);


    @Async
    void sendBookingConfirmationEmail(MailRequestDto request, String customerName, Long room, String roomType, String startDate, String endDate, int guestQuantity, int extraBedsQuantity) throws MessagingException;



   /* @Async
    void sendPasswordRecoveryEmail(MailRequestDto request) throws MessagingException;*/

    @Async
    void sendPasswordRecoveryEmail(MailRequestDto request, String userName) throws MessagingException;

    String deleteTemplate(Long templateId);
}
