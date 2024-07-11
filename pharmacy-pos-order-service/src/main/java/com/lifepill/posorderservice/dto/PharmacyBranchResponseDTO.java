package com.lifepill.posorderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Pharmacy branch response dto.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PharmacyBranchResponseDTO {

    private Double sales;
    private Integer noOfOrders;
    private String managerName;
    private BranchDTO branchDTO;
}