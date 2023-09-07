package com.fiona.savingsManagementApi.SavingsProduct.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SavingsProductPayload {
    @NotBlank(message = " savings product name is required")
    private String name;
}
