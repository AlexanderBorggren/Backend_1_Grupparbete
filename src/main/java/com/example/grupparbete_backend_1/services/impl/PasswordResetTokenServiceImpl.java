package com.example.grupparbete_backend_1.services.impl;

import com.example.grupparbete_backend_1.models.PasswordResetToken;
import com.example.grupparbete_backend_1.models.User;
import com.example.grupparbete_backend_1.repositories.PasswordResetTokenRepo;
import com.example.grupparbete_backend_1.repositories.UserRepo;
import com.example.grupparbete_backend_1.services.PasswordResetTokenService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {



    private final UserRepo userRepo;
    private final PasswordResetTokenRepo passwordResetTokenRepo;

    public PasswordResetTokenServiceImpl(PasswordResetTokenRepo passwordResetTokenRepo, UserRepo userRepo) {
        this.passwordResetTokenRepo = passwordResetTokenRepo;
        this.userRepo = userRepo;
    }

    public PasswordResetToken createPasswordResetTokenForUser(String username) {

        String token = generateToken();
        User user = userRepo.findByUsername(username);
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepo.save(myToken);
        return myToken;
    }

    public Optional<PasswordResetToken> getToken(String token) {

        return passwordResetTokenRepo.findByToken(token);
    }


    public boolean isTokenValid(String validToken) {
        PasswordResetToken token = getToken(validToken).get();
        token.new Date(cal.getTime()
        return getToken(token).isPresent() && !getToken(token).get().isExpired();
    }// isTokenValid



    public String generateToken() {
        return UUID.randomUUID().toString();
    }
}

