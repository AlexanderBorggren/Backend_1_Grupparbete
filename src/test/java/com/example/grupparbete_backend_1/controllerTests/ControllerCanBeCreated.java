package com.example.grupparbete_backend_1.controllerTests;

import com.example.grupparbete_backend_1.controllers.BookingControllerTH;
import com.example.grupparbete_backend_1.controllers.CustomerControllerTH;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ControllerCanBeCreated {

    //Autowired skapar upp en faktisk instans utan att behöva använda new
    @Autowired
    private BookingControllerTH bookingControllerTH;

    @Autowired
    private CustomerControllerTH customerControllerTH;

    @Test
    public void contextLoads() throws Exception{
        assertThat(bookingControllerTH).isNotNull();
        assertThat(customerControllerTH).isNotNull();
    }


}
