package com.example.grupparbete_backend_1.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

@Configuration
public class ThymeleafConfiguration implements WebMvcConfigurer {

    @Bean
    public ClassLoaderTemplateResolver templateResolver() {

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();


        templateResolver.setPrefix("templates/");
        templateResolver.setCacheable(false); //Turned off to facilitate template changes
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");


        return templateResolver;


    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();

        engine.addTemplateResolver(templateResolver());

        return engine;
    }


    @Bean
    public ITemplateResolver stringTemplateResolver() {
    final StringTemplateResolver templateResolver = new StringTemplateResolver();
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setCacheable(false);
    return templateResolver;
    }



    @Bean
    public SpringTemplateEngine stringTemplateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(stringTemplateResolver()); // Use the StringTemplateResolver
        return engine;
    }


}
