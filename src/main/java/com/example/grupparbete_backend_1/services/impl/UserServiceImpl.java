package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.dto.*;
import com.example.grupparbete_backend_1.models.User;
import com.example.grupparbete_backend_1.repositories.RoleRepo;
import com.example.grupparbete_backend_1.repositories.ShippersRepo;
import com.example.grupparbete_backend_1.repositories.UserRepo;
import com.example.grupparbete_backend_1.models.Role;
import com.example.grupparbete_backend_1.securities.ConcreteUserDetails;
import com.example.grupparbete_backend_1.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    public UserServiceImpl(RoleRepo roleRepo, UserRepo userRepo) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
    }

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
    public void addUser(DetailedCustomerDto customer) {
        customerRepo.save(detailedCustomerDtoToCustomer(customer));
    }
    public List<UserDto> getAllUsers() {
        return userRepo.findAll().stream().map(k -> userToUserDTO(k)).toList();
    }


}
