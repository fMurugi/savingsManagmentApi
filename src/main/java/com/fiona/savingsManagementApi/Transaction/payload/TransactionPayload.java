package com.fiona.savingsManagementApi.Transaction.payload;

import com.fiona.savingsManagementApi.Transaction.model.PaymentMethod;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionPayload {
    @NotNull(message = "Payment method is required")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Min(value = 500,message = "amount cannot be less than 500ksh")
    private int amount;

    @NotNull(message = "Customer ID is required")
    private UUID customerId;

    @NotNull(message = "Savings product ID is required")
    private UUID savingsProductId;
}
