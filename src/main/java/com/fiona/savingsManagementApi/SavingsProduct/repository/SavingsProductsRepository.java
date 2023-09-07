package com.fiona.savingsManagementApi.SavingsProduct.repository;

import com.fiona.savingsManagementApi.SavingsProduct.model.SavingsProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SavingsProductsRepository extends JpaRepository<SavingsProductModel, UUID> {
}
