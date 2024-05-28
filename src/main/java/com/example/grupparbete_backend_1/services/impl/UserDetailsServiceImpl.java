package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.models.User;
import com.example.grupparbete_backend_1.repositories.UserRepo;
import com.example.grupparbete_backend_1.securities.ConcreteUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

    @Service
    public class UserDetailsServiceImpl implements UserDetailsService {

        @Autowired
        private UserRepo userRepo;
        @Override
        public UserDetails loadUserByUsername(String username)
                throws UsernameNotFoundException {
            User user = userRepo.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("Could not find user");
            }

            return new ConcreteUserDetails(user);
        }

    }

