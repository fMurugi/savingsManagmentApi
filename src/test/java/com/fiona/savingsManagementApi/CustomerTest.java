package com.fiona.savingsManagementApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiona.savingsManagementApi.Customer.Controller.CustomerController;
import com.fiona.savingsManagementApi.Customer.Model.CustomerModel;
import com.fiona.savingsManagementApi.Customer.Payload.CustomerPayload;
import com.fiona.savingsManagementApi.Customer.repository.CustomerRepository;
import com.fiona.savingsManagementApi.Customer.service.CustomerService;
import com.fiona.savingsManagementApi.SavingsProduct.model.SavingsProductModel;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerTest {
    @Autowired
    private CustomerService customerService;
    @MockBean
    private CustomerRepository customerRepository;


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

//    @BeforeEach
//    public void setup(){
//        this.mockMvc = MockMvcBuilders
//                .standaloneSetup(CustomerController.class)
//                .build();
//    }

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
    public void testGetCustomers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/savings/customers/getAllCustomers")
                .accept("application/json")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists());
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
        customerModel.setNationalId(2345670);
        customerModel.setPhoneNumber("0765097810");
        customerModel.setEmail("cathyCook@gmail.com");
        customerModel.setMemberNumber("M06520");
        customerModel.setSavingsProducts(Collections.emptyList());

        when(customerRepository.save(any())).thenReturn(customerModel);

        String jsonResponse = customerService.createCustomer(customer1);

        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

//        assertEquals(expectedCustomerId, jsonNode.get("customerId").asText());
        assertEquals("cathy", jsonNode.get("firstName").asText());
        assertEquals("cook", jsonNode.get("lastName").asText());
        assertEquals(2345670, jsonNode.get("nationalId").asInt());
        assertEquals("0765097810", jsonNode.get("phoneNumber").asText());
        assertEquals("cathyCook@gmail.com", jsonNode.get("email").asText());
        assertEquals("M06520", jsonNode.get("memberNumber").asText());
        assertTrue(jsonNode.get("savingsProducts").isArray());
        assertEquals(0, jsonNode.get("savingsProducts").size());

//        String expectedJsonResponse = "{\"firstName\":\"cathy\",\"lastName\":\"cook\",\"nationalId\":2345670,\"phoneNumber\":\"0765097810\",\"email\":\"cathyCook@gmail.com\",\"memberNumber\":\"M06520\",\"savingsProducts\":[],\"customerId\":expectedCustomerId}";
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/api/v1/savings/customers/createANewCustomer")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(customer1)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(content().json(expectedJsonResponse));

    }

    @Test
    public void getOneCustomerTest(){
        UUID customerId = UUID.randomUUID();

        CustomerModel expectedCustomer =  new CustomerModel();
        expectedCustomer.setCustomerId(customerId);
        expectedCustomer.setFirstName("cathy");
        expectedCustomer.setLastName("cook");
        expectedCustomer.setNationalId(2345670);
        expectedCustomer.setPhoneNumber("0765097810");
        expectedCustomer.setEmail("cathyCook@gmail.com");
        expectedCustomer.setMemberNumber("M06520");
        expectedCustomer.setSavingsProducts(Collections.emptyList()); // Set an empty list of UUIDs

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(expectedCustomer));

        CustomerModel actualCustomer = customerService.getOneCustomer(customerId);

        assertEquals(expectedCustomer,actualCustomer);

    }
    @Test
    public void deleteCustomerTest(){
        UUID customerId = UUID.randomUUID();

        customerService.deleteCustomer(customerId);
        verify(customerRepository,times(1)).deleteById(customerId);
    }

    @Test
    public void updateCustomerTest(){
        UUID customerId = UUID.randomUUID();
        CustomerPayload customer1 = new CustomerPayload();
        customer1.setFirstName("cathy");
        customer1.setLastName("cook");
        customer1.setNationalId(23456780);
        customer1.setPhoneNumber("0765097810");
        customer1.setEmail("cathyCook@gmail.com");
        customer1.setSavingsProducts(Collections.emptyList());

        CustomerModel existingCustomer =  new CustomerModel();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));

      CustomerModel updatedCustomer = customerService.updateCustomerModel(customer1,customerId);
        //        assertEquals(expectedCustomerId, jsonNode.get("customerId").asText());
        assertEquals("cathy", updatedCustomer.getFirstName());
        assertEquals("cook", updatedCustomer.getLastName());
        assertEquals(23456780, updatedCustomer.getNationalId());
        assertEquals("0765097810", updatedCustomer.getPhoneNumber());
        assertEquals("cathyCook@gmail.com", updatedCustomer.getEmail());
        assertEquals("M06520", updatedCustomer.getMemberNumber());

    }

}
