package com.fiona.savingsManagementApi.IntegrationTests;


import com.fiona.savingsManagementApi.Customer.Model.CustomerModel;
import com.fiona.savingsManagementApi.Customer.Payload.CustomerPayload;
import jakarta.json.Json;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
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

//      String responseEntity=restTemplate.postForObject(baseUrl+"/createANewCustomer",customerModel, String.class);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create an HttpEntity with the payload and headers
        HttpEntity<CustomerPayload> requestEntity = new HttpEntity<>(customerModel, headers);

        // Send a POST request to create a new customer
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(baseUrl + "/createANewCustomer", requestEntity, String.class);


        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//        assertEquals(1,testH2Repository.findAll().size());

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
        // Create a test customer
        CustomerModel testCustomer = new CustomerModel();
        testCustomer.setCustomerId(UUID.fromString("3f0f17a2-2493-4c74-ae61-5beef34d1e02"));
        testCustomer.setFirstName("Julia");
        testCustomer.setLastName("Mark");
        testCustomer.setPhoneNumber("9876543210");
        testCustomer.setNationalId(987654321);
        testCustomer.setEmail("julia@example.com");

        // Save the test customer to the database (assuming you have a customerRepository)
        CustomerModel savedCustomer = testH2Repository.save(testCustomer);

        // Define the URL with the known customer ID
        UUID customerId = savedCustomer.getCustomerId();
        String customerUrl = "http://localhost:" + port + "/api/v1/savings/customers/getOneCustomer/{customerId}";

        ResponseEntity<Customer> responseEntity = restTemplate.getForEntity(customerUrl, Customer.class, customerId);

        // Check the response status
        assertEquals(200, responseEntity.getStatusCodeValue());

        // Verify the retrieved customer
        Object retrievedCustomer = responseEntity.getBody();
        assertNotNull(retrievedCustomer);
//        System.out.println(retrievedCustomer);
//        assertEquals(customerId,retrievedCustomer.getCustomerId());

    }
}

