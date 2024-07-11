package com.lifePill.SupplierService.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Schema(
        name = "Supplier",
        description = "Schema to hold Suppliers information"
)

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
    @NotEmpty(message = "Supplier name cannot be null or empty.")
    private String supplierName;
    private String supplierAddress;
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits.")
    private String supplierPhone;
    @Email(message = "Email is not valid", regexp="{(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])}")
    private String supplierEmail;
    private String supplierDescription;
    private String supplierImage;
    private long supplierRating;
}
