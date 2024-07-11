package com.lifepill.posinventoryservice.service.APIClient;

import com.lifepill.posinventoryservice.util.StandardResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*@FeignClient(
        url = "http://localhost:8082/lifepill/v1",
        value = "SUPPLIER-SERVICE"
)*/
@FeignClient(name = "BRANCH-SERVICE/lifepill/v1")
public interface APIClientBranchService {
    @GetMapping(value = "branch/check-branch-exits-by-id")
    ResponseEntity<StandardResponse> checkBranchExistsById(
            @RequestParam(value = "branchId") int branchId
    );

}
