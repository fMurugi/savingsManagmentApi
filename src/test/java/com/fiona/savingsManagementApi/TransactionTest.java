package com.fiona.savingsManagementApi;

import com.fiona.savingsManagementApi.Customer.Model.CustomerModel;
import com.fiona.savingsManagementApi.Customer.service.CustomerService;
import com.fiona.savingsManagementApi.SavingsProduct.model.SavingsProductModel;
import com.fiona.savingsManagementApi.Transaction.model.PaymentMethod;
import com.fiona.savingsManagementApi.Transaction.model.TransactionModel;
import com.fiona.savingsManagementApi.Transaction.payload.TransactionPayload;
import com.fiona.savingsManagementApi.Transaction.repository.TransactionRepository;
import com.fiona.savingsManagementApi.Transaction.service.TransactionService;
import com.fiona.savingsManagementApi.classes.CustomerTotalSavings;
import com.fiona.savingsManagementApi.classes.TotalSavingsReceived;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionTest {
    @MockBean
    private TransactionRepository transactionRepository;
        @Autowired
        private TransactionService transactionService;

        @Autowired
        private CustomerService customerService;

        @Autowired
        private MockMvc mockMvc;

        @Test
        public void createTransactionTest(){
            UUID customerId =UUID.randomUUID();
            UUID savingsProductId = UUID.randomUUID();

            CustomerModel customerModel = new CustomerModel();
            customerModel.setCustomerId(customerId);
            customerModel.setFirstName("cathy");
            customerModel.setLastName("cook");
            customerModel.setNationalId(2345670);
            customerModel.setPhoneNumber("0765097810");
            customerModel.setEmail("cathyCook@gmail.com");
            customerModel.setMemberNumber("M06520");

            when(customerService.findCustomerById(customerId)).thenReturn(customerModel);

            TransactionPayload payload = new TransactionPayload();
            payload.setPaymentMethod(PaymentMethod.Mpesa);
            payload.setAmount(10000);
            payload.setCustomerId(customerId);
            payload.setSavingsProductId(savingsProductId);


            TransactionModel transactionModel = new TransactionModel();
            transactionModel.setPaymentMethod(PaymentMethod.Mpesa);
            transactionModel.setAmount(10000);
            transactionModel.setCustomerModel(customerModel);
//            transactionModel.setSavingsProductModel(savingsProductModel);


            when(transactionRepository.save(any())).thenReturn(transactionModel);

            TransactionModel createdTransaction = transactionService.createTransaction(payload);

            Assertions.assertNotNull(createdTransaction);
    }

    @Test
    public void  getAllTransactionsTest() {
        List<TransactionModel> transactionModelList = new ArrayList<>();
        CustomerModel customerModel = new CustomerModel();
        SavingsProductModel savingsProductModel = new SavingsProductModel();

        TransactionModel transactionModel = TransactionModel.builder()
                        .paymentMethod(PaymentMethod.Mpesa)
                        .amount(10000)
                        .customerModel(customerModel)
                        .savingsProductModel(savingsProductModel)
                        .build();
        when(transactionRepository.findAll()).thenReturn(transactionModelList);

        List<TransactionModel> retrievedTransactions = transactionService.getAllTransactions();

        assertEquals(transactionModelList,retrievedTransactions);

    }

    @Test
    public void getOneTransactionTest(){
            UUID id = UUID.randomUUID();
            TransactionModel transactionModel = new TransactionModel();
            transactionModel.setTransactionId(id);

            when(transactionRepository.findById(id)).thenReturn(Optional.of(transactionModel));

            TransactionModel retrievedTransaction = transactionService.getOneTransaction(id);

            assertEquals(transactionModel,retrievedTransaction);

    }

    @Test
    public void getCustomerTotalSavingsTest() {
        UUID customerId = UUID.randomUUID();
        UUID savingsProductId = UUID.randomUUID();
        CustomerTotalSavings customerTotalSavings =CustomerTotalSavings.builder()
                .customerId(customerId)
                .savingsProductId(savingsProductId)
                .totalSavings((long)20000)
                .build();

        List<CustomerTotalSavings> sampleTotalSavingsList = Arrays.asList(customerTotalSavings);

        when(transactionRepository.getTotalSavingsPerCustomerAndProduct(customerId, savingsProductId)).thenReturn(sampleTotalSavingsList);

        List<CustomerTotalSavings> retrievedTotalSavings = transactionService.getCustomerTotalSavings(customerId, savingsProductId);

        assertEquals(sampleTotalSavingsList, retrievedTotalSavings);

    }

    @Test
    public void getTotalSavingsReceivedTest() throws Exception {
        UUID savingsProductId = UUID.randomUUID();

        TotalSavingsReceived totalSavingsReceived = TotalSavingsReceived.builder()
                .savingsProductId(savingsProductId)
                .savingsProductName("education")
                .totalSavings(30000000)
                .build();

        List<TotalSavingsReceived> totalSavingsReceivedList = Arrays.asList(totalSavingsReceived);

        when(transactionRepository.getTotalSavingsPerProduct(savingsProductId)).thenReturn(totalSavingsReceivedList);

        List<TotalSavingsReceived> retrievedTotalSavingsReceived = transactionService.getTotalSavingsReceived(savingsProductId);

        assertEquals(totalSavingsReceivedList, retrievedTotalSavingsReceived);

//        mockMvc.perform(get("/api/v1/savings/transactions/getTotalSavingsReceived/savingsProductId"))
//                .andExpect(status().isOk())
//                .andReturn();
    }

}
