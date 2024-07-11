package com.lifepill.posorderservice.controller;

import com.lifepill.posorderservice.dto.PharmacyBranchResponseDTO;
import com.lifepill.posorderservice.service.BranchSummaryService;
import com.lifepill.posorderservice.util.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class to handle branch summary related endpoints.
 */
@RestController
@RequestMapping("lifepill/v1/branch-summary")
@AllArgsConstructor
public class BranchSummaryController {

    private BranchSummaryService branchSummaryService;

    /**
     * gti checkRetrieves all branches along with their sales summary.
     *
     * @return ResponseEntity containing StandardResponse with status 201 (SUCCESS)
     * and list of PharmacyBranchResponseDTO
     */
    @GetMapping("/sales-summary")
    public ResponseEntity<StandardResponse> getAllBranchesWithSales() {
        List<PharmacyBranchResponseDTO> allBranchesWithSales = branchSummaryService.getAllBranchesWithSales();
        return new ResponseEntity<>(
                new StandardResponse(
                        201,
                        "SUCCESS",
                        allBranchesWithSales
                ),
                HttpStatus.OK
        );
    }
}
