package com.example.grupparbete_backend_1.configurations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BlacklistProperties {
    private String url;
    private String emailtocheck;
}
