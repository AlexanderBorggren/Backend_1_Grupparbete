package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.dto.MailRequestDto;
import com.example.grupparbete_backend_1.dto.UserDto;
import com.example.grupparbete_backend_1.models.PasswordResetToken;
import com.example.grupparbete_backend_1.models.User;
import com.example.grupparbete_backend_1.services.*;
import jakarta.mail.MessagingException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URISyntaxException;


@Controller
public class LoginController {
    private final UserService userService;
    private final EmailingService emailingService;
    private final PasswordResetTokenService passwordResetTokenService;
    public LoginController(UserService userService, EmailingService emailingService, PasswordResetTokenService passwordResetTokenService) {
        this.emailingService = emailingService;
        this.userService = userService;
        this.passwordResetTokenService = passwordResetTokenService;

    }

    @GetMapping("/login")
    String login() {
        return "login";
    }
    @GetMapping("/login/resetpassword")
    String forgotpassword() {
        return "resetpassword";
    }

    @PostMapping("/login/forgotpassword")
    String createResetToken(@RequestParam String email) throws IOException, URISyntaxException, InterruptedException, MessagingException {

        System.out.println(email);
        if (userService.doesUserWithUsernameExist(email)) {
            PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetTokenForUser(email);

            String tokenLink = "http://localhost:8080/login/newpassword/" + passwordResetToken.getToken();
            MailRequestDto sendResetLink = new MailRequestDto();
            sendResetLink.setToEmail(email);
            sendResetLink.setTemplateName("PasswordRecovery");
            sendResetLink.setHTML(true);

            emailingService.sendPasswordRecoveryEmail(sendResetLink, tokenLink);
        }

        return "resetlinksent";
    }

    @RequestMapping("/login/newpassword/{token}")
    public String updateUserFormView(@PathVariable String token, Model model) throws IOException, URISyntaxException, InterruptedException {

        if (!passwordResetTokenService.isTokenValid(token)) {
            return "invalidToken";
        }

        User user = passwordResetTokenService.getToken(token).getUser();
        UserDto userDto = userService.userToUserDTO(user);

        model.addAttribute( "user", userDto);
        return "passwordResetForm";
    }

    @PostMapping("/login/update")
    public String updateUser(UserDto userDto, Model model, RedirectAttributes redirectAttributes) throws IOException, URISyntaxException, InterruptedException {


        UserDto userDtoFromDB = userService.getUserByID(userDto.getId());

        if (userService.doesUsernameExistExceptSelf(userDtoFromDB.getUsername(), userDtoFromDB.getId())) {
            redirectAttributes.addFlashAttribute("feedbackMessage", "User with username" + userDtoFromDB.getUsername() + " already exists.");
            return "failedResetPassword";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashPassword = encoder.encode(userDto.getPassword());
        userDtoFromDB.setPassword(hashPassword);


        userService.addUser(userDtoFromDB);

        redirectAttributes.addFlashAttribute("feedbackMessage", "User " + userDtoFromDB.getUsername() + " has been updated.");

        return "hasResetPassword";
    }

}

