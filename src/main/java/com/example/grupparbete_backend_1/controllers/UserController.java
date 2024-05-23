package com.example.grupparbete_backend_1.controllers;


import com.example.grupparbete_backend_1.dto.*;
import com.example.grupparbete_backend_1.models.Role;
import com.example.grupparbete_backend_1.models.User;
import com.example.grupparbete_backend_1.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@Validated
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/all")
    public String getAll(Model model) throws IOException, URISyntaxException, InterruptedException {
        List<UserDto> allUsers = userService.getAllUsers();

        model.addAttribute("allUsers", allUsers);



        model.addAttribute("usersTitle", "All Users: ");
        model.addAttribute("email", "Username: ");
        model.addAttribute("roles", "Roles: ");
        model.addAttribute("enabled", "Enabled: ");
        return "users";
    }

    @RequestMapping(path = "/deleteByEmail/{email}/")
    public String deleteUser(@PathVariable String email, RedirectAttributes redirectAttributes) {
        String message = userService.deleteUser(email);
        redirectAttributes.addFlashAttribute("message", message);


        return "redirect:/users/all";
    }

    //CREATE NEW BOOKING
    @RequestMapping(value = "/addBooking")
    public String addUser(@RequestParam("mail") String mail,
                          @RequestParam("group") String group,
                             RedirectAttributes redirectAttributes) throws URISyntaxException, IOException, InterruptedException {

        String feedbackMessage = userService.addUser(mail, group);
        redirectAttributes.addFlashAttribute("feedbackMessageCreateBooking", feedbackMessage);

        return "redirect:/user/all";
    }


    /*
    @PostMapping("/update")
    public String updateCustomer(@Valid Model model, DetailedCustomerDto c, RedirectAttributes redirectAttributes) {
        String id = String.valueOf(c.getId());
        if (customerService.doesSsnExistUpdate(c.getSsn(), c.getId())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Customer with SSN " + c.getSsn() + " already exists.");
            return "redirect:/customer/editByView/" + id + "/";
        }
        customerService.addCustomer(c);
        model.addAttribute("name", "name");
        model.addAttribute("ssn", "ssn");
        model.addAttribute("email", "email");

        String feedbackMessage = "Customer id " + c.getId() + " with name " + c.getName() + " has been updated.";
        redirectAttributes.addFlashAttribute("updateCustomerFeedbackMessage", feedbackMessage);

        return "redirect:/customer/all";
    }
    */

    @RequestMapping("/addUserView")
    public String createUserByForm(Model model) {
        List<Role> allRoles = userService.getAllRoles();
        model.addAttribute("allRoles", allRoles);

        return "addUserForm";
    }


    @PostMapping("/addUser")
    public String addUser(@Valid @RequestParam String username, @RequestParam String password, @RequestParam List<Role> roles, Model model, RedirectAttributes redirectAttributes) {

        model.addAttribute("username", username);
        model.addAttribute("password", password);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashPassword = encoder.encode(password);
        model.addAttribute("roles", roles);

        if (userService.doesUserWithUsernameExist(username)) {
            model.addAttribute("errorMessage", "User with username " + username + " already exists.");
            List<Role> allRoles = userService.getAllRoles();
            model.addAttribute("allRoles", allRoles);
            return "addUserForm";
        } else {
            List<Role> uniqueRoles = roles.stream().distinct().toList();
            userService.addUser(new UserDto(username, hashPassword, uniqueRoles, true));
            String feedbackMessage = "User " + username + " has been added.";
            redirectAttributes.addFlashAttribute("feedbackMessageCreateBooking", feedbackMessage);

            return "redirect:/users/all";
        }
    }





}
