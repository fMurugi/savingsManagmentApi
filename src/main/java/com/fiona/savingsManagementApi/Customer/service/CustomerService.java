//package com.fiona.savingsManagementApi.Customer.service;
//
//import com.fiona.savingsManagementApi.Customer.Model.CustomerModel;
//import com.fiona.savingsManagementApi.Customer.Payload.CustomerPayload;
//import com.fiona.savingsManagementApi.Customer.repository.CustomerRepository;
//import lombok.AllArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Service;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Random;
//import java.util.UUID;
//
//@Service
//@AllArgsConstructor
//public class CustomerService {
//    private CustomerRepository customerRepository;
//    private ModelMapper modelMapper;
//    private static   int counter = 0;
//
//    //create a new customer
//    public CustomerModel createCustomer(CustomerPayload customerPayload){
//        CustomerModel customerModel = modelMapper.map(customerPayload,CustomerModel.class);
//        //generate member number
//        String generatedMemberNumber = generateMemberNumber();
//        customerModel.setMemberNumber(generatedMemberNumber);
//
//        return customerRepository.save(customerModel);
//    }
//
//    //get all customers
//    public List<CustomerModel> getAllCustomers(){
//        return customerRepository.findAll();
//    }
//
//    //get one customer
//    public CustomerModel getOneCustomer(UUID id){
//        return customerRepository.findById(id).orElseThrow();
//    }
//
//
//    public static String generateMemberNumber() {
//        counter++;
//        String paddedCounter = String.format("%05d", counter); // Padded with leading zeros
//        return "M" + paddedCounter;
//    }
//
//}
