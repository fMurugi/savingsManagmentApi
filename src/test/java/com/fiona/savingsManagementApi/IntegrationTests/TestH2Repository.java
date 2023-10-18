package com.fiona.savingsManagementApi.IntegrationTests;

import com.fiona.savingsManagementApi.Customer.Model.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2Repository extends JpaRepository<CustomerModel,Integer> {

}