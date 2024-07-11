package com.lifepill.employeeService.controller;

import com.lifepill.employeeService.dto.responseDTO.EmployerAllDetailsDTO;
import com.lifepill.employeeService.service.EmployerService;
import com.lifepill.employeeService.util.StandardResponse;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("lifepill/v1/branch-employer")
@AllArgsConstructor
public class EmployerBranchController {

    private EmployerService employerService;

    /**
     * Endpoint for retrieving all employers (cashiers) associated with a specific branch.
     *
     * @param branchId The ID of the branch
     * @return ResponseEntity containing a list of employer DTOs
     */
    //TODO: add employer bank details
    @GetMapping("/employer/by-branch/{branchId}")
    public ResponseEntity<StandardResponse> getAllCashiersByBranchId(@PathVariable int branchId) {
        List<EmployerAllDetailsDTO> employerAllDetailsDTOS = employerService.getAllEmployerByBranchId(branchId);
        return new ResponseEntity<>(
                new StandardResponse(
                        HttpStatus.OK.value(),
                        "Employers retrieved successfully",
                        employerAllDetailsDTOS
                ),
                HttpStatus.OK
        );
    }

    /**
     * Endpoint for retrieving the manager associated with a specific branch.
     *
     * @param branchId The ID of the branch
     * @return ResponseEntity containing the manager details
     * This endpoint is a GET request mapped to "/manager/by-branch/{branchId}".
     * It retrieves the manager associated with the branch specified by the branchId path variable.
     * The manager details are retrieved by calling the getManagerByBranchId method of the EmployerService.
     * The response is a ResponseEntity containing a StandardResponse object.
     * The StandardResponse object contains the HTTP status code, a success message, and the manager details.
     */
    @GetMapping("/manager/by-branch/{branchId}")
    public ResponseEntity<StandardResponse> getManagerByBranchId(@PathVariable int branchId) {
        EmployerAllDetailsDTO managerDetails = employerService.getManagerByBranchId(branchId);
        if (managerDetails == null) {
            return new ResponseEntity<>(
                    new StandardResponse(
                            HttpStatus.OK.value(),
                            "Manager not found for branch ID " + branchId,
                            null
                    ),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new StandardResponse(
                            HttpStatus.OK.value(),
                            "Manager retrieved successfully",
                            managerDetails
                    ),
                    HttpStatus.OK
            );
        }
    }
}