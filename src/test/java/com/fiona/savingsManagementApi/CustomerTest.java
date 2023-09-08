package com.fiona.savingsManagementApi;

import com.fiona.savingsManagementApi.Customer.Model.CustomerModel;
import com.fiona.savingsManagementApi.Customer.repository.CustomerRepository;
import com.fiona.savingsManagementApi.Customer.service.CustomerService;
import com.fiona.savingsManagementApi.SavingsProduct.model.SavingsProductModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerTest {
    @MockBean
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;

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

    
}
