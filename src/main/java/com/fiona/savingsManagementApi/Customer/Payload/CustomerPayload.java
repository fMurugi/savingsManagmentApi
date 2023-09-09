package com.fiona.savingsManagementApi.Customer.Payload;

import jakarta.validation.Valid;
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
    @Valid

    @NotNull(message = "first name is required")
    @NotBlank(message = "First name is required")
    @NotEmpty(message = "First name is required")
    private String firstName;
    @NotBlank(message = "last name is required")
    private String lastName;
    @NotNull(message="national id is required")
//    @Min(value=8)
//    @Max(value = 8)
    private int nationalId;
    @NotNull(message = "phone number is required")
    @Pattern(regexp = "^07\\d{8}$", message = "Invalid phone number format")
    private String phoneNumber;


    @NotBlank(message = "Email is required")
    @Email(message = "email should be valid")
    private String email;
    @NotNull(message = "Savings product IDs are required")
    private List<UUID> savingsProducts;

}
