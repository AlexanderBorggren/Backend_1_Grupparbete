package com.example.grupparbete_backend_1.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@ConfigurationPropertiesScan
@Configuration
@ConfigurationProperties(prefix = "integrations")
@Data
public class IntegrationProperties {
    private BlacklistProperties blacklist;
    private int integrationNumber;
}
