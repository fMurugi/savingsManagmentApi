package com.fiona.savingsManagementApi.Transaction.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fiona.savingsManagementApi.Customer.Model.CustomerModel;
import com.fiona.savingsManagementApi.SavingsProduct.model.SavingsProductModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
@Entity
@Immutable
public class TransactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="transaction_id")
    private UUID transactionId;

    @Column(name="created_at")
    private LocalDateTime createdAt;
    @Column(name="payment_method")
    @Enumerated(EnumType.STRING)
    private  PaymentMethod paymentMethod;
    @Column(name="amount")
    private int amount;
    @ManyToOne
    @JoinColumn(name="customer_id")
    private CustomerModel customerModel;
    @ManyToOne
    @JoinColumn(name="savings_product_id")
    @JsonManagedReference
    private SavingsProductModel savingsProductModel;
    @PrePersist
    protected void onCreate(){
        this.createdAt =LocalDateTime.now();
    }
}
