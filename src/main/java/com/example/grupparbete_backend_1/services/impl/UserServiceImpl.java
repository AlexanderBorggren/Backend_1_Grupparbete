package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.dto.*;
import com.example.grupparbete_backend_1.models.BlacklistCheckResponse;
import com.example.grupparbete_backend_1.models.Booking;
import com.example.grupparbete_backend_1.models.User;
import com.example.grupparbete_backend_1.repositories.RoleRepo;
import com.example.grupparbete_backend_1.repositories.UserRepo;
import com.example.grupparbete_backend_1.securities.Role;
import com.example.grupparbete_backend_1.services.BlacklistService;
import com.example.grupparbete_backend_1.services.CustomerService;
import com.example.grupparbete_backend_1.services.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Repository

public class UserServiceImpl implements UserService {

    RoleRepo roleRepo;
    UserRepo userRepo;

    @Override
    public UserDto userToUserDTO(User user) {
        return UserDto.builder()
                .roles(user.getRoles().stream().toList())
                .email(user.getUsername())
                .enabled(user.isEnabled())
                .build();
    }

    public String addUser(String mail, String group) {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(roleRepo.findByName(group));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("Hejsan123#");
        User user = User.builder().enabled(true).password(hash).username(mail).roles(roles).build();
        userRepo.save(user);
        return "Added new user : " + mail;
    }

    public void addRole(String name) {
        Role role = new Role();
        roleRepo.save(Role.builder().name(name).build());
    }

    public List<UserDto> getAllUsers() {
        return userRepo.findAll().stream().map(k -> userToUserDTO(k)).toList();
    }

}
