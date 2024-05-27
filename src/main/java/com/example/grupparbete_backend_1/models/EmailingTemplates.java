package com.example.grupparbete_backend_1.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class EmailingTemplates {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String templateName;

    private String templateDescription;

    private String subject;

    private String fromEmail;

    @Lob
    @Column(length = 100000) // or whatever size is appropriate
    private String body;



}
