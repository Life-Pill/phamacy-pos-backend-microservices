package com.lifepill.posinventoryservice.dto.responseDTO;

import com.lifepill.posinventoryservice.dto.ItemCategoryDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ItemGetResponseWithoutSupplierDetailsDTO {
        private ItemGetAllResponseDTO itemGetAllResponseDTO;
        private ItemCategoryDTO itemCategoryDTO;
}
