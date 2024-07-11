package com.lifepill.posinventoryservice.dto;

import lombok.*;

/**
 * The type Supplier dto.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SupplierDTO {
    private long supplierId;
    private long companyId;
    private String supplierName;
    private String supplierAddress;
    private String supplierPhone;
    private String supplierEmail;
    private String supplierDescription;
    private String supplierImage;
    private String supplierRating;

}
