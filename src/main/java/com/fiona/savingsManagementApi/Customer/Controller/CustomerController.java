package com.fiona.savingsManagementApi.Customer.Controller;

import com.fiona.savingsManagementApi.Customer.Model.CustomerModel;
import com.fiona.savingsManagementApi.Customer.Payload.CustomerPayload;
import com.fiona.savingsManagementApi.Customer.service.CustomerService;
import com.fiona.savingsManagementApi.utils.APiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

import static com.fiona.savingsManagementApi.utils.APIResponseBuilder.buildResponseEntity;

@RestController
@RequestMapping("api/v1/savings/customers")
@AllArgsConstructor
@NoArgsConstructor
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/createANewCustomer")
    public ResponseEntity<APiResponse> createCustomer(@Valid @RequestBody  CustomerPayload payload, HttpServletRequest request){

        String CustomerModel = customerService.createCustomer(payload);
        return buildResponseEntity(HttpStatus.CREATED,CustomerModel,request.getRequestURI());
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<APiResponse> getAllCustomers(HttpServletRequest request){
        List<CustomerModel> CustomerModelList = customerService.getAllCustomers();
        return buildResponseEntity(HttpStatus.OK,CustomerModelList,request.getRequestURI());
    }

    @GetMapping("/getOneCustomer/{id}")
    public  ResponseEntity<APiResponse> getOneSavingsProduct(@PathVariable UUID id, HttpServletRequest request){
        CustomerModel CustomerModel = customerService.getOneCustomer(id);
        return buildResponseEntity(HttpStatus.OK,CustomerModel,request.getRequestURI());
    }

    @PutMapping("/updateCustomer/{id}")
    public ResponseEntity<APiResponse> updateSavingsProduct(@PathVariable UUID id,@RequestBody CustomerPayload payload, HttpServletRequest request){
        CustomerModel CustomerModel = customerService.updateCustomerModel(payload,id);

        return buildResponseEntity(HttpStatus.OK,CustomerModel,request.getRequestURI());
    }

    @DeleteMapping("/deletedCustomer/{id}")
    public String deleteCustomer(@PathVariable UUID id) {
        return customerService.deleteCustomer(id);
    }
}
