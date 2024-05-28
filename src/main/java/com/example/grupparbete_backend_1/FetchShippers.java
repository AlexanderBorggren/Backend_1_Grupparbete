package com.example.grupparbete_backend_1;

import com.example.grupparbete_backend_1.dto.ShippersDto;
import com.example.grupparbete_backend_1.models.Shippers;
import com.example.grupparbete_backend_1.services.ShippersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

@ComponentScan
public class FetchShippers implements CommandLineRunner {

    @Autowired
    private ShippersService shippersService;

    public void run(String... args) throws Exception {

        shippersService.fetchShippers();

       /* ObjectMapper objectMapper = new ObjectMapper();

        List<Shippers> theShippers = Arrays.asList(objectMapper.readValue(new URL("https://javaintegration.systementor.se/shippers"), Shippers[].class));

        for (Shippers s : theShippers) {
            System.out.println(s.getExternal_Shippers_Id());
            System.out.println(s.getCompanyName());

            ShippersDto shippersDto = shippersService.shippersToShippersDto(s);

            Shippers existingShippers = shippersService.getShippersByExternalId(s.getExternal_Shippers_Id());
            if (existingShippers == null) {
                shippersService.addShippers(shippersDto);
            }
        }*/
    }
}