package com.example.grupparbete_backend_1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GrupparbeteBackend1Application.class)
public class ControllersTests {

    @Value(value="${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAllCustomers() throws Exception{
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/customer/all", String.class)).contains("All customers");
    }



}
