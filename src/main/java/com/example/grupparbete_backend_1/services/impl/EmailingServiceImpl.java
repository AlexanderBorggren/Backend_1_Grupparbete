package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.dto.MailRequestDto;
import com.example.grupparbete_backend_1.models.EmailingTemplates;
import com.example.grupparbete_backend_1.repositories.EmailTemplateRepo;
import com.example.grupparbete_backend_1.services.EmailingService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailingServiceImpl implements EmailingService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;


    private final EmailTemplateRepo emailTemplateRepo;

    /*@Value("${spring.mail.username}")
    private String fromMail;*/


    @Override
    public String getTemplate(String templateName){

        EmailingTemplates templates = emailTemplateRepo.findByTemplateName(templateName);

        return templates !=null ? templates.getBody() : null;
    }

    @Override
    public EmailingTemplates  createTemplate(String templateName, String subject, String body){
                EmailingTemplates newTemplates = new EmailingTemplates();
                newTemplates.setTemplateName(templateName);
                newTemplates.setSubject(subject);
                newTemplates.setBody(body);

                return emailTemplateRepo.save(newTemplates);
    }




    @Override
    @Async
    public void sendEmail(MailRequestDto request) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setFrom(request.getFromEmail());
        mimeMessageHelper.setTo(request.getToEmail());
        mimeMessageHelper.setSubject(request.getSubject());

        if(request.isHTML()){
            Context context = new Context();

            context.setVariable("content", request.getBody());
            String processedString = templateEngine.process("BookingEmailConfirmationTemplate", context);

            mimeMessageHelper.setText(processedString, true);
        }
        else{
            mimeMessageHelper.setText(request.getBody(), false);

        }

        mailSender.send(mimeMessage);

    }


}
