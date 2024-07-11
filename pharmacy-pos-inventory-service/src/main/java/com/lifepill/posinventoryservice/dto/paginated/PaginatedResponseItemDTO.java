package com.lifepill.posinventoryservice.dto.paginated;

import com.lifepill.posinventoryservice.dto.responseDTO.ItemGetResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaginatedResponseItemDTO {
    List<ItemGetResponseDTO> list;
    private long dataCount;
}
