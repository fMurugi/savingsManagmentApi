package com.fiona.savingsManagementApi.Customer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiona.savingsManagementApi.Customer.Model.CustomerModel;
import com.fiona.savingsManagementApi.Customer.Payload.CustomerPayload;
import com.fiona.savingsManagementApi.Customer.repository.CustomerRepository;
import com.fiona.savingsManagementApi.Exceptions.ResourceNotFoundException;
import com.fiona.savingsManagementApi.SavingsProduct.model.SavingsProductModel;
import com.fiona.savingsManagementApi.SavingsProduct.payload.SavingsProductPayload;
import com.fiona.savingsManagementApi.SavingsProduct.repository.SavingsProductsRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
public class CustomerService {
    private CustomerRepository customerRepository;
    private SavingsProductsRepository savingsProductsRepository;
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final Random random = new Random();
    //create a new customer
    public String createCustomer(CustomerPayload payload) {
        CustomerModel customerModel = new CustomerModel();
        customerModel.setFirstName(payload.getFirstName());
        customerModel.setLastName(payload.getLastName());
        customerModel.setPhoneNumber(payload.getPhoneNumber());
        customerModel.setNationalId(payload.getNationalId());
        customerModel.setEmail(payload.getEmail());        //generate member number
        String generatedMemberNumber = generateMemberNumber();
        customerModel.setMemberNumber(generatedMemberNumber);

        List<SavingsProductModel> savingsProductModelList = new ArrayList<>();

        for (UUID id : payload.getSavingsProducts()) {
            Optional<SavingsProductModel> savingsProductOptional = savingsProductsRepository.findById(id);
            savingsProductOptional.ifPresent(savingsProductModelList::add);
        }
        customerModel.setSavingsProducts(savingsProductModelList);
         CustomerModel savedCustomer= customerRepository.save(customerModel);

         try{
             String json = objectMapper.writeValueAsString(savedCustomer);
             return json;
         } catch (JsonProcessingException e) {
             throw new RuntimeException(e);
         }

    }

    //get all customers
    public List<CustomerModel> getAllCustomers() {
        return customerRepository.findAll();
    }

    //get one customer
    public CustomerModel getOneCustomer(UUID id) {
        return findCustomerById(id);
    }

    //update customer
    public CustomerModel updateCustomerModel(CustomerPayload payload, UUID id) {
        CustomerModel customerModel = findCustomerById(id);
        customerModel.setFirstName(payload.getFirstName());
        customerModel.setLastName(payload.getLastName());
        customerModel.setPhoneNumber(payload.getPhoneNumber());
        customerModel.setNationalId(payload.getNationalId());
        customerModel.setEmail(payload.getEmail());

        if (payload.getSavingsProducts() != null) {
            List<SavingsProductModel> savingsProductModelList = new ArrayList<>();

            for (UUID savingsProductsId : payload.getSavingsProducts()) {

                Optional<SavingsProductModel> savingsProductOptional = savingsProductsRepository.findById(savingsProductsId);
                savingsProductOptional.ifPresent(savingsProductModelList::add);
            }
            customerModel.setSavingsProducts(savingsProductModelList);
        }

        return customerRepository.save(customerModel);
    }

    //deleted customer
    public String deleteCustomer(UUID id) {
        customerRepository.deleteById(id);
        return "Customer successfully deleted";
    }


    public static String generateMemberNumber() {
        int randomCounter = random.nextInt(10000);
        String paddedCounter = String.format("%05d", randomCounter);
        return "M" + paddedCounter;
    }

    public CustomerModel findCustomerById(UUID id) {
        Optional<CustomerModel> customerModel = customerRepository.findById(id);

        if (!customerModel.isPresent()) {
            throw new ResourceNotFoundException("Customer.not found");
        } else {
            return customerModel.get();
        }

    }
}
