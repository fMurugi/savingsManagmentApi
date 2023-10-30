package com.fiona.savingsManagementApi.IntegrationTests;


import com.fiona.savingsManagementApi.Customer.Model.CustomerModel;
import com.fiona.savingsManagementApi.Customer.Payload.CustomerPayload;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Customer {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private TestH2Repository testH2Repository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();

    }

    @BeforeEach
    public void setup() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/v1/savings/customers");
    }

    @Test
    public void testAddProduct() {

        CustomerPayload customerModel = new CustomerPayload();
        customerModel.setFirstName("cathy");
        customerModel.setLastName("cook");
        customerModel.setNationalId(2345670);
        customerModel.setPhoneNumber("0765097810");
        customerModel.setEmail("cathyCook@gmail.com");
        customerModel.setSavingsProducts(Collections.emptyList());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CustomerPayload> requestEntity = new HttpEntity<>(customerModel, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(baseUrl + "/createANewCustomer", requestEntity, String.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    }

    @Test
    public void testGetCustomers() {

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(baseUrl + "/getAllCustomers", String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseBody = responseEntity.getBody();
            System.out.println("All Customers: " + responseBody);
        } else {
            System.err.println("Failed to retrieve all customers. Status code: " + responseEntity.getStatusCodeValue());
        }
    }

    @Test
    public void testGetCustomerById() {
        CustomerModel testCustomer = new CustomerModel();
        testCustomer.setCustomerId(UUID.fromString("3f0f17a2-2493-4c74-ae61-5beef34d1e02"));
        testCustomer.setFirstName("Julia");
        testCustomer.setLastName("Mark");
        testCustomer.setPhoneNumber("9876543210");
        testCustomer.setNationalId(987654321);
        testCustomer.setEmail("julia@example.com");

        CustomerModel savedCustomer = testH2Repository.save(testCustomer);

        UUID customerId = savedCustomer.getCustomerId();
        String customerUrl = "http://localhost:" + port + "/api/v1/savings/customers/getOneCustomer/{customerId}";

        ResponseEntity<Customer> responseEntity = restTemplate.getForEntity(customerUrl, Customer.class, customerId);

        assertEquals(200, responseEntity.getStatusCodeValue());

        Object retrievedCustomer = responseEntity.getBody();
        assertNotNull(retrievedCustomer);

    }
}

