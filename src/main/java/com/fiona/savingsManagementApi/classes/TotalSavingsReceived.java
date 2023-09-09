package com.fiona.savingsManagementApi.classes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TotalSavingsReceived {
    private UUID savingsProductId;
    private String savingsProductName;
    private int totalSavings;
}
