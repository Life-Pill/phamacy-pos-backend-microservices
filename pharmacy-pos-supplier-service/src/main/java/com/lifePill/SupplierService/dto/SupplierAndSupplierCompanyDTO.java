package com.lifePill.SupplierService.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SupplierAndSupplierCompanyDTO {
    private SupplierDTO supplierDTO;
    private SupplierCompanyDTO supplierCompanyDTO;
}
