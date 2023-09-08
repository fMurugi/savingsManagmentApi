package com.fiona.savingsManagementApi.Transaction.repository;

import com.fiona.savingsManagementApi.Transaction.model.TransactionModel;
import com.fiona.savingsManagementApi.classes.CustomerTotalSavings;
import com.fiona.savingsManagementApi.classes.TotalSavingsReceived;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, UUID> {
//    List<TransactionModel> getTransactionByCustomerModel(UUID id);
    @Query("SELECT NEW com.fiona.savingsManagementApi.classes.CustomerTotalSavings(" +
            "t.customerModel.id, t.savingsProductModel.id, SUM(t.amount)) " +
            "FROM TransactionModel t " +
            "WHERE t.customerModel.id = :customerId " +
            "AND t.savingsProductModel.id = :savingsProductId " +
            "GROUP BY t.customerModel.id, t.savingsProductModel.id")
    List<CustomerTotalSavings> getTotalSavingsPerCustomerAndProduct(
            @Param("customerId") UUID customerId,
            @Param("savingsProductId") UUID savingsProductId
    );

//    @Query("SELECT NEW com.fiona.savingsManagementApi.classes.TotalSavingsReceived(" +
//            "t.savingsProductModel.id AS savingsProductId, SUM(t.amount) AS totalSavings) " +
//            "FROM TransactionModel t " +
//            "WHERE t.savingsProductModel.id = :savingsProductId " +
//            "GROUP BY t.savingsProductModel.id")
//    List<TotalSavingsReceived> getTotalSavingsPerProduct(
//            @Param("savingsProductId") UUID savingsProductId
//    );
    @Query("SELECT NEW com.fiona.savingsManagementApi.classes.TotalSavingsReceived(" +
            "t.savingsProductModel.id, t.savingsProductModel.name, CAST(SUM(t.amount) AS int)) " +
            "FROM TransactionModel t " +
            "WHERE t.savingsProductModel.id = :savingsProductId " +
            "GROUP BY t.savingsProductModel.id, t.savingsProductModel.name")
    List<TotalSavingsReceived> getTotalSavingsPerProduct(
            @Param("savingsProductId") UUID savingsProductId
    );
}
