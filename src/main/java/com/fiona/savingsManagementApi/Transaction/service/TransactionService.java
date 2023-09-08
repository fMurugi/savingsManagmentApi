package com.fiona.savingsManagementApi.Transaction.service;

import com.fiona.savingsManagementApi.Customer.Model.CustomerModel;
import com.fiona.savingsManagementApi.Customer.service.CustomerService;
import com.fiona.savingsManagementApi.Exceptions.ResourceNotFoundException;
import com.fiona.savingsManagementApi.SavingsProduct.model.SavingsProductModel;
import com.fiona.savingsManagementApi.SavingsProduct.service.SavingsProductsService;
import com.fiona.savingsManagementApi.Transaction.model.TransactionModel;
import com.fiona.savingsManagementApi.Transaction.payload.TransactionPayload;
import com.fiona.savingsManagementApi.Transaction.repository.TransactionRepository;
import com.fiona.savingsManagementApi.classes.CustomerTotalSavings;
import com.fiona.savingsManagementApi.classes.TotalSavingsReceived;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SavingsProductsService savingsProductsService;

    HashMap<String,String>  customerSavings = new HashMap<>();
    public TransactionModel createTransaction(TransactionPayload payload){
        TransactionModel transactionModel =new TransactionModel();
        transactionModel.setPaymentMethod(payload.getPaymentMethod());
        transactionModel.setAmount(payload.getAmount());

        UUID customerId = payload.getCustomerId();
        CustomerModel customerModel = customerService.findCustomerById(customerId);

        UUID savingsProductId = payload.getSavingsProductId();
        SavingsProductModel savingsProductModel = savingsProductsService.findSavingsProductById(savingsProductId);

        transactionModel.setCustomerModel(customerModel);
        transactionModel.setSavingsProductModel(savingsProductModel);

        return transactionRepository.save(transactionModel);
    }

    //get all customers
    public List<TransactionModel> getAllTransactions(){
        return transactionRepository.findAll();
    }

    //get one customer
    public TransactionModel getOneTransaction(UUID id){
        return findTransactionById(id);
    }

//    public List<TransactionModel> getTransactionsPerCustomer(UUID id){
//        List<TransactionModel>  transactionModelList = transactionRepository.getTransactionByCustomerModel(id);
//        return transactionModelList;
//    }

    public List<CustomerTotalSavings> getCustomerTotalSavings(UUID customerId, UUID savingsProductId){
        return transactionRepository.getTotalSavingsPerCustomerAndProduct(customerId,savingsProductId);
    }

    public List<TotalSavingsReceived> getTotalSavingsReceived(UUID savingsProductId){
        return transactionRepository.getTotalSavingsPerProduct(savingsProductId);
    }


    public TransactionModel findTransactionById(UUID id) {
        return transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction  not found"));
    }
}
