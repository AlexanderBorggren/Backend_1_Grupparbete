package com.example.grupparbete_backend_1.repositories;

import com.example.grupparbete_backend_1.models.EmailingTemplates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailTemplateRepo extends JpaRepository<EmailingTemplates, Long> {
    EmailingTemplates findByTemplateName(String templateName);

}
