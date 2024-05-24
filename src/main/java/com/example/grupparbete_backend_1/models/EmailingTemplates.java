package com.example.grupparbete_backend_1.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class EmailingTemplates {
    @Id
    private Long id;

    private String templateName;

    private String subject;

    private String body;


}
