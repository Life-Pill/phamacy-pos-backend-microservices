package com.lifepill.posorderservice.service;

import com.lifepill.posorderservice.dto.PharmacyBranchResponseDTO;

import java.util.List;

public interface BranchSummaryService {
    List<PharmacyBranchResponseDTO> getAllBranchesWithSales();
}
