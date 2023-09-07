package com.fiona.savingsManagementApi.Customer.repository;

import com.fiona.savingsManagementApi.Customer.Model.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerModel, UUID> {

}
