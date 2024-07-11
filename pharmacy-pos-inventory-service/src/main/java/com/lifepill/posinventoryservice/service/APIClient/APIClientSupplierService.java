package com.lifepill.posinventoryservice.service.APIClient;

import com.lifepill.posinventoryservice.dto.SupplierAndSupplierCompanyDTO;
import com.lifepill.posinventoryservice.util.StandardResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/*@FeignClient(
        url = "http://localhost:8082/lifepill/v1",
        value = "SUPPLIER-SERVICE"
)*/
@FeignClient(name = "SUPPLIER-SERVICE/lifepill/v1")
public interface APIClientSupplierService {

    @GetMapping(path ="/supplier/get-supplier-with-company/{supplierId}")
    SupplierAndSupplierCompanyDTO getSupplierAndCompanyBySupplierId(
            @PathVariable("supplierId") long supplierId
    );

    @GetMapping(path = "/supplier/check-supplier-exists-by-id/{supplierId}")
    ResponseEntity<StandardResponse> checkSupplierExistsById(
            @RequestParam(value = "supplierId") long supplierId
    ) ;
}
