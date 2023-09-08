package com.fiona.savingsManagementApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiona.savingsManagementApi.Customer.Model.CustomerModel;
import com.fiona.savingsManagementApi.Customer.Payload.CustomerPayload;
import com.fiona.savingsManagementApi.Customer.repository.CustomerRepository;
import com.fiona.savingsManagementApi.Customer.service.CustomerService;
import com.fiona.savingsManagementApi.SavingsProduct.model.SavingsProductModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerTest {
    @MockBean
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;
//    @Autowired
//    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllCustomers(){
        CustomerModel customer1 = new CustomerModel();
        customer1.setCustomerId(UUID.fromString("07c1d0a0-88b0-431d-8db8-1e5ad32a007c"));
        customer1.setFirstName("cathy");
        customer1.setLastName("cook");
        customer1.setNationalId(23456780);
        customer1.setPhoneNumber("0765097810");
        customer1.setEmail("cathyCook@gmail.com");
        customer1.setMemberNumber("M06520");

        SavingsProductModel savingsProduct1 = new SavingsProductModel();
        savingsProduct1.setSavingProductId(UUID.fromString("ed188e0f-495f-4bee-9205-6e80be959955"));
        savingsProduct1.setName("education");

        List<SavingsProductModel> savingsProducts1 = new ArrayList<>();
        savingsProducts1.add(savingsProduct1);
        customer1.setSavingsProducts(savingsProducts1);

        List<CustomerModel> expectedCustomers = Arrays.asList(customer1);

        when(customerRepository.findAll()).thenReturn(expectedCustomers);

        List<CustomerModel> actualCustomers = customerService.getAllCustomers();
        assertEquals(expectedCustomers,actualCustomers);
    }

    @Test
    public void createCustomerTest() throws Exception {
        CustomerPayload customer1 = new CustomerPayload();
        customer1.setFirstName("cathy");
        customer1.setLastName("cook");
        customer1.setNationalId(23456780);
        customer1.setPhoneNumber("0765097810");
        customer1.setEmail("cathyCook@gmail.com");


        customer1.setSavingsProducts(Collections.emptyList());

        CustomerModel customerModel = new CustomerModel();
        UUID expectedCustomerId = UUID.randomUUID();
        customerModel.setCustomerId(expectedCustomerId);
        customerModel.setFirstName("cathy");
        customerModel.setLastName("cook");
        customerModel.setNationalId(23456780);
        customerModel.setPhoneNumber("0765097810");
        customerModel.setEmail("cathyCook@gmail.com");
        customerModel.setMemberNumber("M06520");
        customerModel.setSavingsProducts(Collections.emptyList()); // Set an empty list of UUIDs

        when(customerRepository.save(any())).thenReturn(customerModel);

        String jsonResponse = customerService.createCustomer(customer1);

        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

//        assertEquals(expectedCustomerId, jsonNode.get("customerId").asText());
        assertEquals("cathy", jsonNode.get("firstName").asText());
        assertEquals("cook", jsonNode.get("lastName").asText());
        assertEquals(23456780, jsonNode.get("nationalId").asInt());
        assertEquals("0765097810", jsonNode.get("phoneNumber").asText());
        assertEquals("cathyCook@gmail.com", jsonNode.get("email").asText());
        assertEquals("M06520", jsonNode.get("memberNumber").asText());
        assertTrue(jsonNode.get("savingsProducts").isArray());
        assertEquals(0, jsonNode.get("savingsProducts").size());
    }


}
