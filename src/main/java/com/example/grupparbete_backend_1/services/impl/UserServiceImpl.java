package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.dto.*;
import com.example.grupparbete_backend_1.models.Customer;
import com.example.grupparbete_backend_1.models.User;
import com.example.grupparbete_backend_1.repositories.PasswordResetTokenRepo;
import com.example.grupparbete_backend_1.repositories.RoleRepo;
import com.example.grupparbete_backend_1.repositories.ShippersRepo;
import com.example.grupparbete_backend_1.repositories.UserRepo;
import com.example.grupparbete_backend_1.models.Role;
import com.example.grupparbete_backend_1.securities.ConcreteUserDetails;
import com.example.grupparbete_backend_1.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    private final PasswordResetTokenRepo passwordResetTokenRepo;
    public UserServiceImpl(RoleRepo roleRepo, UserRepo userRepo, PasswordResetTokenRepo passwordResetTokenRepo) {
        this.passwordResetTokenRepo = passwordResetTokenRepo;
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
    }

    @Override
    public UserDto userToUserDTO(User user) {
        return UserDto.builder()
                .id(user.getId())
                .roles(user.getRoles().stream().toList())
                .username(user.getUsername())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .build();
    }

    public User userDtoToUser(UserDto user) {
        return User.builder().
                id(user.getId()).
                roles(user.getRoles()).
                username(user.getUsername()).
                enabled(user.isEnabled()).
                password(user.getPassword())
                .build();
    }


    public void addUser(UserDto user) {
        userRepo.save(userDtoToUser(user));
    }
    public List<UserDto> getAllUsers() {
        return userRepo.findAll().stream().map(k -> userToUserDTO(k)).toList();
    }
    public List<Role> getAllRoles() {
        return roleRepo.findAll().stream().toList();
    }
    @Transactional
    public String deleteUser(Long id) {
        User user = userRepo.findById(id).orElse(null);
            passwordResetTokenRepo.deleteByUser(user);

        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!userDetails.getUsername().equals(user.getUsername())) {
            userRepo.delete(user);
            return user.getUsername() + " has been removed.";
        }
        return "You cannot remove the logged in user.";
    }

    @Transactional
    public boolean doesUserWithUsernameExist(String username)
    {
        User user = userRepo.findByUsername(username);
        return user != null;
    }

   public UserDto findUserByUsername(String username){

        return userToUserDTO(userRepo.findByUsername(username));
   }


    public UserDto getUserByID(Long id) {
        return userToUserDTO(Objects.requireNonNull(userRepo.findById(id).orElse(null)));
    }

    public boolean doesUsernameExistExceptSelf(String username, Long userId) {
        List<UserDto> users = getAllUsers();
        return users.stream()
                .anyMatch(user -> user.getUsername().equals(username) && (!user.getId().equals(userId)));
    }

}
