package com.fiona.savingsManagementApi.Customer.Payload;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerPayload {
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "last name is required")
    private String lastName;
    @NotBlank(message="national id is required")
    private int nationalId;
    @NotBlank(message = "phone number is required")
    private int phoneNumber;


    @NotBlank(message = "Email is required")
    @Email(message = "email should be valid")
    private String email;
    @NotNull(message = "Savings product IDs are required")
    private List<UUID> savingsProducts;

}
