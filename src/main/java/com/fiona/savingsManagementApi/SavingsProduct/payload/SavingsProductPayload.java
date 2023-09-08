package com.fiona.savingsManagementApi.SavingsProduct.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingsProductPayload {

    @NotBlank(message = " savings product name is required")
    @NotNull(message = " savings product name is required")
    private String name;
}
