package com.lifePill.SupplierService.repository;

import com.lifePill.SupplierService.entity.SupplierCompany;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Supplier company repository.
 */
public interface SupplierCompanyRepository extends JpaRepository<SupplierCompany, Long> {
}