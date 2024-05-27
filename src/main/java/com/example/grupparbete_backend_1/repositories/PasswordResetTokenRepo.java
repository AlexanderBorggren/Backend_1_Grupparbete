package com.example.grupparbete_backend_1.repositories;

import com.example.grupparbete_backend_1.models.PasswordResetToken;
import com.example.grupparbete_backend_1.models.Role;
import com.example.grupparbete_backend_1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, UUID> {
    PasswordResetToken findByToken(String token);
    void deleteByUser(User user);

}
