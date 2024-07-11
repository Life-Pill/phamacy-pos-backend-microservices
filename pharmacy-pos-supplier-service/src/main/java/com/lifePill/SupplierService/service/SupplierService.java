package com.lifePill.SupplierService.service;

import com.lifePill.SupplierService.dto.SupplierAndSupplierCompanyDTO;
import com.lifePill.SupplierService.dto.SupplierDTO;

import java.util.List;

public interface SupplierService {
    List<SupplierDTO> getAllSuppliers();

    SupplierDTO saveSupplier(SupplierDTO supplierDTO);

    SupplierDTO updateSupplierById(long id, SupplierDTO updatedSupplierDTO);

    void deleteSupplierById(long id);

    SupplierDTO getSupplierById(long id);

    SupplierAndSupplierCompanyDTO getSupplierAndCompanyBySupplierId(long supplierId);

    boolean checkSupplierExistsById(long supplierId);
}
