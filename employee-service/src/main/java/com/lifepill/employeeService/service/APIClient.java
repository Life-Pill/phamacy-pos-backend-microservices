package com.lifepill.employeeService.service;

import com.lifepill.employeeService.dto.BranchDTO;
import com.lifepill.employeeService.util.StandardResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * APIClient is an interface that defines the contract for communication with the BRANCH-SERVICE.
 * It uses Feign as a declarative web service client to simplify the HTTP API calls.
 */
@FeignClient(name = "BRANCH-SERVICE/lifepill/v1")
public interface APIClient {

    /**
     * This method is used to retrieve a branch by its id from the BRANCH-SERVICE.
     * It sends a GET request to the "/branch/get-branch-by-id" endpoint of the BRANCH-SERVICE.
     * The branch id is passed as a request parameter.
     * The method is transactional, meaning that it is part of a larger database transaction.
     * The method returns a ResponseEntity that wraps the StandardResponse from the BRANCH-SERVICE.
     *
     * @param branchId The id of the branch to retrieve.
     * @return A ResponseEntity that contains the StandardResponse from the BRANCH-SERVICE.
     */
    @GetMapping(path = "/branch/get-branch-by-id")
    @Transactional
    ResponseEntity<StandardResponse> getBranchById(@RequestParam(value = "branchId") int branchId);

}