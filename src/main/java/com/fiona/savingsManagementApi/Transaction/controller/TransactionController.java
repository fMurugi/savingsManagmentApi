package com.fiona.savingsManagementApi.Transaction.controller;

import com.fiona.savingsManagementApi.Transaction.model.TransactionModel;
import com.fiona.savingsManagementApi.Transaction.payload.TransactionPayload;
import com.fiona.savingsManagementApi.Transaction.service.TransactionService;
import com.fiona.savingsManagementApi.classes.CustomerTotalSavings;
import com.fiona.savingsManagementApi.classes.TotalSavingsReceived;
import com.fiona.savingsManagementApi.utils.APiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.fiona.savingsManagementApi.utils.APIResponseBuilder.buildResponseEntity;

@RestController
@RequestMapping("/api/v1/savings/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @PostMapping("/createANewTransaction")
    public ResponseEntity<APiResponse> createTransaction(@RequestBody @Valid TransactionPayload payload, HttpServletRequest request){

        TransactionModel transactionModel = transactionService.createTransaction(payload);
        return buildResponseEntity(HttpStatus.CREATED,transactionModel,request.getRequestURI());
    }

    @GetMapping("/getAllTransactions")
    public ResponseEntity<APiResponse> getAllTransactions(HttpServletRequest request){
        List<TransactionModel> transactionModelList = transactionService.getAllTransactions();
        return buildResponseEntity(HttpStatus.OK,transactionModelList,request.getRequestURI());
    }

    @GetMapping("/getOneTransaction/{id}")
    public  ResponseEntity<APiResponse> getOneTransaction(@PathVariable UUID id, HttpServletRequest request){
        TransactionModel transactionModel = transactionService.getOneTransaction(id);
        return buildResponseEntity(HttpStatus.OK,transactionModel,request.getRequestURI());
    }

    @GetMapping("/getTotalSavingsPerCustomer/{customerId}/{savingsProductId}")
    public  ResponseEntity<APiResponse> getTotalSavingsPerCustomer(@PathVariable UUID customerId,@PathVariable UUID savingsProductId,HttpServletRequest request){
    List<CustomerTotalSavings> customerTotalSavings = transactionService.getCustomerTotalSavings(customerId,savingsProductId);
    return buildResponseEntity(HttpStatus.OK,customerTotalSavings,request.getRequestURI());
    }

    @GetMapping("/getTotalSavingsReceived/{savingsProductId}")
    public  ResponseEntity<APiResponse> getTotalSavingsReceived(@PathVariable UUID savingsProductId,HttpServletRequest request){
        List<TotalSavingsReceived> totalSavingsReceivedList = transactionService.getTotalSavingsReceived(savingsProductId);
        return buildResponseEntity(HttpStatus.OK,totalSavingsReceivedList,request.getRequestURI());
    }
}
