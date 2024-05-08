package com.example.grupparbete_backend_1.controllers;

import com.example.grupparbete_backend_1.dto.DetailedRoomTypeDto;
import com.example.grupparbete_backend_1.dto.ShippersDto;
import com.example.grupparbete_backend_1.services.ShippersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShippersController {

        private final ShippersService shippersService;

    @RequestMapping("getAllShippers")
    public List<ShippersDto> getAllRoomTypes(){
        return shippersService.getAllShippers();
    }

}
