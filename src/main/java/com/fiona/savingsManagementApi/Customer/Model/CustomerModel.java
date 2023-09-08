package com.fiona.savingsManagementApi.Customer.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fiona.savingsManagementApi.SavingsProduct.model.SavingsProductModel;
import com.fiona.savingsManagementApi.Transaction.model.TransactionModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name="customers")
@AllArgsConstructor
@NoArgsConstructor
public class CustomerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name= "customer_id")
    private UUID customerId;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="id_number")
    private int nationalId;
    @Column(name="phone_number")
    private String phoneNumber;
    @Column(name ="email")
    private String email;
    @Column(name="member_number",unique = true)
    private String memberNumber;
    @ManyToMany
    @JoinTable(name = "customer_savings_product",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    @JsonManagedReference
    private List<SavingsProductModel> savingsProducts = new ArrayList<>();

}
