package com.example.grupparbete_backend_1.services;

import com.example.grupparbete_backend_1.dto.BlacklistedCustomerDto;
import com.example.grupparbete_backend_1.dto.DetailedBookingDto;
import com.example.grupparbete_backend_1.dto.UserDto;
import com.example.grupparbete_backend_1.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface UserService {

    public UserDto userToUserDTO(User user) throws IOException, InterruptedException, URISyntaxException;
    public String addUser(String mail, String group) throws IOException, InterruptedException, URISyntaxException;

    public void addRole(String name) throws IOException, InterruptedException, URISyntaxException;
    public List<UserDto> getAllUsers() throws IOException, InterruptedException, URISyntaxException;
}
