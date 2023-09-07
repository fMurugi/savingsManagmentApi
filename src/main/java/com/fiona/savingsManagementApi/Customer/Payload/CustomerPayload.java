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
    private String firstname;
    @NotBlank(message = "last name is required")
    private String lastname;
    @NotBlank(message="national id is required")
    @Pattern(regexp = "\\d{8}", message = "National ID must be an 8-digit number")
    private int nationalId;
    @NotBlank(message = "phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be a 10-digit number")
    private int phoneNumber;
    @NotBlank(message = "Email is required")
    @Email(message = "email should be valid")
    private String email;
    @NotNull(message = "Savings product IDs are required")
    private List<UUID> savingsProductIds;

}
