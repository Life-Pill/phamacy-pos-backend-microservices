package com.lifepill.posorderservice.service.APIClient;

import com.lifepill.posorderservice.util.StandardResponse;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "BRANCH-SERVICE/lifepill/v1")
public interface APIClientBranchService {

    @GetMapping(value = "branch/check-branch-exits-by-id")
    ResponseEntity<StandardResponse> checkBranchExistsById(
            @RequestParam(value = "branchId") int branchId
    );

    @GetMapping(path = "branch/get-branch-by-id", params = "branchId")
    @Transactional
    ResponseEntity<StandardResponse> getBranchById(
            @RequestParam(value = "branchId") int branchId
    );
}
