package com.example.grupparbete_backend_1.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "integration")
@Data
public class IntegrationProperties {
    private BlacklistProperties blacklist;
    private JsonStreamProperties jsonStream;
    private XmlProperties xml;
    private EventProperties eventProperties;
    private int integrationNumber;
}
