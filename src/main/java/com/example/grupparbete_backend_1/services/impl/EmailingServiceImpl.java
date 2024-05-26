package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.dto.MailRequestDto;
import com.example.grupparbete_backend_1.models.EmailingTemplates;
import com.example.grupparbete_backend_1.repositories.EmailTemplateRepo;
import com.example.grupparbete_backend_1.services.EmailingService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailingServiceImpl implements EmailingService {


    private final JavaMailSender mailSender;

    private final EmailTemplateRepo emailTemplateRepo;

    /*@Value("${spring.mail.username}")
    private String fromMail;*/

    public ITemplateResolver stringTemplateResolver() {
        final StringTemplateResolver templateResolver = new StringTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);
        return templateResolver;
    }
    public SpringTemplateEngine stringTemplateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(stringTemplateResolver()); // Use the StringTemplateResolver
        return engine;
    }

    @Override
    public EmailingTemplates getTemplateWithTemplateName(String templateName){

        return emailTemplateRepo.findByTemplateName(templateName);
    }

    @Override
    public EmailingTemplates getTemplate(Long id) {
        return emailTemplateRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid template Id:" + id));
    }


    @Override
    public List<EmailingTemplates> getAllTemplates(){
        return emailTemplateRepo.findAll();
    }



    @Override
    public EmailingTemplates createTemplate(String templateName, String description, String subject, String body){
                EmailingTemplates newTemplate = new EmailingTemplates();

                newTemplate.setTemplateName(templateName);
                newTemplate.setTemplateDescription(description);
                newTemplate.setSubject(subject);
                newTemplate.setBody(body);

                return emailTemplateRepo.save(newTemplate);
    }

    @Override
    public EmailingTemplates updateTemplate(Long id, String newTemplateName, String newTemplateDescription, String newSubject, String newBody){
        return emailTemplateRepo.findById(id).map(emailTemplate -> {
            emailTemplate.setTemplateName(newTemplateName);
            emailTemplate.setTemplateDescription(newTemplateDescription);
            emailTemplate.setSubject(newSubject);
            emailTemplate.setBody(newBody);

            return emailTemplateRepo.save(emailTemplate);
        }).orElseThrow(() -> new IllegalArgumentException("Invalid template Id:" + id));
    }




    @Override
    @Async
    public void sendEmail(MailRequestDto request, String customerName, Long room,String roomType, String startDate, String endDate, int guestQuantity, int extraBedsQuantity) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setFrom(request.getFromEmail());
        mimeMessageHelper.setTo(request.getToEmail());



        if(request.isHTML()){

            EmailingTemplates template = getTemplateWithTemplateName(request.getTemplateName());

            System.out.println("inne i metoden med template id:" + template.getId() + customerName + room + roomType + startDate + endDate + guestQuantity + extraBedsQuantity);

            Context context = new Context();

            context.setVariable("customerName", customerName);
            context.setVariable("room", room);
            context.setVariable("roomType", roomType);
            context.setVariable("startDate", startDate);
            context.setVariable("endDate", endDate);
            context.setVariable("guestQuantity", guestQuantity);
            context.setVariable("extraBedsQuantity", extraBedsQuantity);

            String processedString = stringTemplateEngine().process(template.getBody(), context);
            mimeMessageHelper.setSubject(template.getSubject());
            mimeMessageHelper.setText(processedString, true);
        } else {
            System.out.println("FASTNADE HÄR");
            mimeMessageHelper.setText(request.getBody(), false);

        }

        mailSender.send(mimeMessage);

    }

    @Override
    @Async
    public void sendEmail(MailRequestDto request, String userName) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setFrom(request.getFromEmail());
        mimeMessageHelper.setTo(request.getToEmail());
        mimeMessageHelper.setSubject(request.getSubject());

        if(request.isHTML()){
            Context context = new Context();

            context.setVariable("content", request.getBody());
            String processedString = stringTemplateEngine().process("BookingEmailConfirmationTemplate", context);

            mimeMessageHelper.setText(processedString, true);
        }
        else{
            mimeMessageHelper.setText(request.getBody(), false);

        }

        mailSender.send(mimeMessage);

    }



    @Override
    @Async
    public void sendConfirmationEmail(MailRequestDto request) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setFrom(request.getFromEmail());
        mimeMessageHelper.setTo(request.getToEmail());
        mimeMessageHelper.setSubject(request.getSubject());

        if(request.isHTML()){
            Context context = new Context();

            context.setVariable("content", request.getBody());
            String processedString = stringTemplateEngine().process("BookingEmailConfirmationTemplate", context);

            mimeMessageHelper.setText(processedString, true);
        }
        else{
            mimeMessageHelper.setText(request.getBody(), false);

        }

        mailSender.send(mimeMessage);

    }

    @Override
    public String deleteTemplate(Long templateId){

        EmailingTemplates templates = emailTemplateRepo.findById(templateId).get();

        emailTemplateRepo.deleteById(templateId);

        return templates.getTemplateName() + " has been deleted";

    }


}
