package com.example.grupparbete_backend_1.controllers;

import ch.qos.logback.core.model.Model;
import com.example.grupparbete_backend_1.dto.ShippersDto;
import com.example.grupparbete_backend_1.services.ShippersService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Validated
@RequestMapping("/shippers")

public class ShippersControllerTH {

        private final ShippersService shippersService;

        public ShippersControllerTH(ShippersService shippersService) {
                this.shippersService = shippersService;
        }

        @RequestMapping("/all")
        public String getAll(Model model) {
                List<ShippersDto> s = shippersService.getAllShippers();
                return "shippers";
        }
}
