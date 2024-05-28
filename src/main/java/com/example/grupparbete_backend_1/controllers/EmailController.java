package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.dto.MailRequestDto;
import com.example.grupparbete_backend_1.models.EmailingTemplates;
import com.example.grupparbete_backend_1.services.EmailingService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@RequiredArgsConstructor
@Controller
@RequestMapping("/emailView")
public class EmailController {

    private final EmailingService emailingService;

    @GetMapping("/sendConfirmation")
    @ResponseStatus(HttpStatus.OK)
    public void sendConfirmationEmail(@RequestParam String customerName,
                                      @RequestParam(required = false) Long room,
                                      @RequestParam(required = false) String roomType,
                                      @RequestParam(required = false) String startDate,
                                      @RequestParam(required = false) String endDate,
                                      @RequestParam(required = false) int extraBedsQuantity,
                                      @RequestParam(required = false) int guestQuantity) throws MessagingException {
        MailRequestDto mailRequestDto = new MailRequestDto();

        mailRequestDto.setTemplateName("BookingConfirmation");
        mailRequestDto.setFromEmail("autoreply@booking.pensionatet.com");
        mailRequestDto.setToEmail(customerName);
        mailRequestDto.setHTML(true);

        emailingService.sendBookingConfirmationEmail(mailRequestDto, customerName, room, roomType, startDate, endDate, extraBedsQuantity,guestQuantity);
    }



    @RequestMapping("/all")
    public String getAllEmailTemplates(Model model){
        List<EmailingTemplates> templates = emailingService.getAllTemplates();

        model.addAttribute("allTemplates", templates);
        model.addAttribute("emailTemplateTitle", "Email Template");
        model.addAttribute("id", "Id");
        model.addAttribute("templateName", "Template name");
        model.addAttribute("templateDescription", "Template description");

       return "emailTemplatesView";

    }

    @RequestMapping(path = "/deleteById/{id}/")
    public String deleteTemplate(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        String message = emailingService.deleteTemplate(id);
        redirectAttributes.addFlashAttribute("deleteTemplateFeedbackMessage", message);

        return "redirect:/emailView/all";
    }

    @RequestMapping("/editByView/{id}/")
    public String updateByForm(@PathVariable Long id, Model model) {
        try {
            EmailingTemplates emailingTemplate = emailingService.getTemplate(id);
            model.addAttribute("emailTemplate", emailingTemplate);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
        }

        return "updateEmailTemplateForm";
    }



    @PostMapping("/updateTemplate")
    public String updateTemplate(Model model, @ModelAttribute EmailingTemplates newTemplate, RedirectAttributes redirectAttributes) {
        try {

            emailingService.updateTemplate(newTemplate.getId(), newTemplate.getTemplateName(), newTemplate.getTemplateDescription(), newTemplate.getSubject(), newTemplate.getBody(), newTemplate.getFromEmail());

            model.addAttribute("emailTemplate", newTemplate);
            model.addAttribute("emailTemplateTitle", "Email Template");
            model.addAttribute("id", "Id");
            model.addAttribute("templateName", "Template name");
            model.addAttribute("templateDescription", "Template description");


            redirectAttributes.addFlashAttribute("updateTemplateFeedbackMessage", "Template updated successfully");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("updateTemplateFeedbackMessage", "Error updating template: " + e.getMessage());
        }

        return "redirect:/emailView/all";
    }


    @RequestMapping("/addTemplateView")
    public String createTemplateByForm(Model model) {
        return "addTemplateForm";
    }

    @PostMapping("/addTemplate")
    public String createTemplate(Model model, @ModelAttribute EmailingTemplates newTemplate, RedirectAttributes redirectAttributes) {

        model.addAttribute("emailTemplate", newTemplate);
        model.addAttribute("emailTemplateTitle", "Email Template");
        model.addAttribute("id", "Id");
        model.addAttribute("templateName", "Template name");
        model.addAttribute("templateDescription", "Template description");


        emailingService.createTemplate(newTemplate.getTemplateName(), newTemplate.getTemplateDescription(), newTemplate.getSubject(), newTemplate.getBody(),newTemplate.getFromEmail());

        redirectAttributes.addFlashAttribute("addTemplateFeedbackMessage", "Template added successfully");


        return "redirect:/emailView/all";

    }

}
