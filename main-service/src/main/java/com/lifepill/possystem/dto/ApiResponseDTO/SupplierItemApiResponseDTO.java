package com.lifepill.possystem.dto.ApiResponseDTO;

import com.lifepill.possystem.dto.SupplierAndSupplierCompanyDTO;
import com.lifepill.possystem.dto.responseDTO.ItemGetIdResponseDTO;
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
