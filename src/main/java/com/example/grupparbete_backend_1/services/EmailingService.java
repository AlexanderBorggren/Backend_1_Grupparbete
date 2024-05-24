package com.example.grupparbete_backend_1.services;

import com.example.grupparbete_backend_1.dto.MailRequestDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailingService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromMail;


    @Async
    public void sendEmail(MailRequestDto request) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setFrom(fromMail);
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
