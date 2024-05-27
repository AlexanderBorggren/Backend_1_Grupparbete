package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.dto.MailRequestDto;
import com.example.grupparbete_backend_1.dto.UserDto;
import com.example.grupparbete_backend_1.models.PasswordResetToken;
import com.example.grupparbete_backend_1.models.User;
import com.example.grupparbete_backend_1.services.*;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;


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

            String tokenResponse = "Press this link to reset your password: http://localhost:8080/login/newpassword/" + passwordResetToken.getToken();
            System.out.println(tokenResponse);

            MailRequestDto sendResetLink = new MailRequestDto("noreply@pensionatet.se",
                    email,
                    "Reset Password",tokenResponse,
                    false, "resetPassword");
            emailingService.sendEmail(sendResetLink, email);
        }

        return "resetlinksent";
    }

    @RequestMapping("/login/newpassword/{token}")
    public String updateUserForm(@PathVariable String token, Model model) throws IOException, URISyntaxException, InterruptedException {

        if (!passwordResetTokenService.isTokenValid(token)) {
            model.addAttribute("message", "Invalid token");
            return "invalidtoken";
        }

        User user = passwordResetTokenService.getToken(token).get().getUser();
        userService.userToUserDTO(user);

        model.addAttribute( "User", user);
        return "resetPasswordForm";
    }

}

