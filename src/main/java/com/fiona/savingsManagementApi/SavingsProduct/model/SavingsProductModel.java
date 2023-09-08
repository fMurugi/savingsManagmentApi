package com.fiona.savingsManagementApi.SavingsProduct.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fiona.savingsManagementApi.Customer.Model.CustomerModel;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Table(name="savings_products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingsProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="saving_product_id")
    private UUID savingProductId;
    @Column(name="name",nullable = false,unique = true)
    private String name;

    @ManyToMany(mappedBy = "savingsProducts")
    @JsonBackReference
    private List<CustomerModel> customers = new ArrayList<>();

}
