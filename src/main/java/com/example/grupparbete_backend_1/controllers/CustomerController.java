package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.dto.BlacklistedCustomerDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
public class CustomerController {

   private final WebClient webClient;

    public CustomerController(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("https://javabl.systementor.se/api/stefan/blacklist").build();
    }
    @GetMapping("/getBlacklist")
    public Mono<List<BlacklistedCustomerDto>> getBlacklist(){
        return webClient.get()
                .retrieve()
                .bodyToFlux(BlacklistedCustomerDto.class)
                .collectList();

    }

}


