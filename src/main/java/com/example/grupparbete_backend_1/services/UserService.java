package com.example.grupparbete_backend_1.services;

import com.example.grupparbete_backend_1.dto.BlacklistedCustomerDto;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface UserService {

    public void addUser(String mail, String group) throws IOException, InterruptedException, URISyntaxException;
    public void addRole(String name) throws IOException, InterruptedException, URISyntaxException;
}
