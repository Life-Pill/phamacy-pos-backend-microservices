package com.lifepill.possystem.controller;

import com.lifepill.possystem.dto.EmployerDTO;
import com.lifepill.possystem.service.BranchService;
import com.lifepill.possystem.service.EmployerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller class for managing branch-related operations by branch managers.
 */
@RestController
@RequestMapping("lifepill/v1/branch-manager")
@AllArgsConstructor
public class BranchManagerController {

    private BranchService branchService;
    private EmployerService employerService;

    /**
     * Retrieves all cashiers (employers) associated with a specific branch managed by the branch manager.
     *
     * @param branchId The ID of the branch for which cashiers are to be retrieved.
     * @return A ResponseEntity containing a list of EmployerDTO objects representing the cashiers.
     */
    @GetMapping("/employer/by-branch-manager/{branchId}")
    public ResponseEntity<List<EmployerDTO>> getAllCashiersByBranchId(@PathVariable int branchId) {
        List<EmployerDTO> employerDTOS = employerService.getAllEmployerByBranchId(branchId);
        return new ResponseEntity<>(employerDTOS, HttpStatus.OK);
    }
}
