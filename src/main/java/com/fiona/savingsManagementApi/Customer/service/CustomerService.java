package com.fiona.savingsManagementApi.Customer.service;

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
    private ModelMapper modelMapper;
    private static   int counter = 0;

    //create a new customer
    public CustomerModel createCustomer(CustomerPayload payload){
        CustomerModel customerModel =new CustomerModel();
        customerModel.setFirstName(payload.getFirstName());
        customerModel.setLastName(payload.getLastName());
        customerModel.setPhoneNumber(payload.getPhoneNumber());
        customerModel.setNationalId(payload.getNationalId());
        customerModel.setEmail(payload.getEmail());        //generate member number
        String generatedMemberNumber = generateMemberNumber();
        customerModel.setMemberNumber(generatedMemberNumber);

        List<SavingsProductModel>  savingsProductModelList = new ArrayList<>();

        for(UUID id: payload.getSavingsProducts()){
            Optional<SavingsProductModel> savingsProductOptional = savingsProductsRepository.findById(id);
            savingsProductOptional.ifPresent(savingsProductModelList::add);
        }
        customerModel.setSavingsProducts(savingsProductModelList);
        return customerRepository.save(customerModel);
    }

    //get all customers
    public List<CustomerModel> getAllCustomers(){
        return customerRepository.findAll();
    }

    //get one customer
    public CustomerModel getOneCustomer(UUID id){
        return findCustomerById(id);
    }

    //update customer
    public CustomerModel updateCustomerModel(CustomerPayload payload, UUID id){
        CustomerModel customerModel = findCustomerById(id);
        customerModel.setFirstName(payload.getFirstName());
        customerModel.setLastName(payload.getLastName());
        customerModel.setPhoneNumber(payload.getPhoneNumber());
        customerModel.setNationalId(payload.getNationalId());
        customerModel.setEmail(payload.getEmail());

        if(payload.getSavingsProducts()!=null){
            List<SavingsProductModel>  savingsProductModelList = new ArrayList<>();

            for(UUID savingsProductsId: payload.getSavingsProducts()){

                Optional<SavingsProductModel> savingsProductOptional = savingsProductsRepository.findById(savingsProductsId);
                savingsProductOptional.ifPresent(savingsProductModelList::add);
            }
            customerModel.setSavingsProducts(savingsProductModelList);
        }

        return customerRepository.save(customerModel);
    }

    //deleted customer
    public String deleteCustomer(UUID id){
        customerRepository.deleteById(id);
        return "Customer successfully deleted";
    }


    public static String generateMemberNumber() {
        counter++;
        String paddedCounter = String.format("%05d", counter); // Padded with leading zeros
        return "M" + paddedCounter;
    }

    public CustomerModel findCustomerById(UUID id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }
}
