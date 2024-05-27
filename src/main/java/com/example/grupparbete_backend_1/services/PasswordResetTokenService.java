package com.example.grupparbete_backend_1.services;

import com.example.grupparbete_backend_1.models.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenService {
    public String generateToken();
    public PasswordResetToken createPasswordResetTokenForUser(String username);
    public Optional<PasswordResetToken> getToken(String token);
}
