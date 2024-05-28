package com.example.grupparbete_backend_1.dto;


import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailRequestDto {

    private String fromEmail;

    @JsonAlias(value="to_email")
    private String toEmail;

    private String subject;

    private String body;

    @JsonAlias(value="html")
    private boolean isHTML;

    private String templateName;

}
