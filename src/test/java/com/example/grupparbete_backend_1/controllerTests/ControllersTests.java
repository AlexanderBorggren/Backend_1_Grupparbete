package com.example.grupparbete_backend_1.controllerTests;

import com.example.grupparbete_backend_1.GrupparbeteBackend1Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * Tests the functionality of controllers by sending HTTP requests to the web server.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GrupparbeteBackend1Application.class)
public class ControllersTests {
    // Injects the port number used for testing from application.properties
    @Value(value="${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    /**
     * Tests the functionality of the 'getAllCustomers' endpoint by sending an HTTP GET request
     * and verifying that the response contains the expected string "All customers".
     */
    @Test
    public void getAllCustomers() throws Exception{
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/customer/all", String.class)).contains("All customers");
    }

    @Test
    public void getAllBookings() throws Exception{
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/booking/all", String.class)).contains("All bookings");
    }




}
