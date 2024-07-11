package com.lifepill.posinventoryservice.dto.ApiResponseDTO;


import com.lifepill.posinventoryservice.dto.SupplierAndSupplierCompanyDTO;
import com.lifepill.posinventoryservice.dto.responseDTO.ItemGetIdResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierItemApiResponseDTO {
    private ItemGetIdResponseDTO itemGetIdResponseDTO;
    private SupplierAndSupplierCompanyDTO supplierAndSupplierCompanyDTO;
}
