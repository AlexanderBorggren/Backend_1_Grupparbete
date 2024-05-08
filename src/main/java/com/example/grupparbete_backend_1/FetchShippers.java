package com.example.grupparbete_backend_1;

import com.example.grupparbete_backend_1.dto.ShippersDto;
import com.example.grupparbete_backend_1.models.Shippers;
import com.example.grupparbete_backend_1.services.ShippersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
@ComponentScan
public class FetchShippers implements CommandLineRunner {

    @Autowired
    private ShippersService shippersService;

    public void run(String... args) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        List<Shippers> theShippers = Arrays.asList(objectMapper.readValue(new URL("https://javaintegration.systementor.se/shippers"), Shippers[].class));

        for (Shippers s : theShippers) {
            System.out.println(s.getExternal_Shippers_Id());
            System.out.println(s.getCompanyName());

            ShippersDto shippersDto = shippersService.shippersToShippersDto(s);


            shippersService.addShippers(shippersDto);
        }
    }
}