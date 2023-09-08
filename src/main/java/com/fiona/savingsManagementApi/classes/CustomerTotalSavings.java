package com.fiona.savingsManagementApi.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerTotalSavings {
    private UUID customerId;
    private UUID savingsProductId;
    private Long totalSavings;
}
