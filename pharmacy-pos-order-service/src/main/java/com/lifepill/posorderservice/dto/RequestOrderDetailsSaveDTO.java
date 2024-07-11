package com.lifepill.posorderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The type Request order details save dto.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestOrderDetailsSaveDTO {
    private String itemName;
    private Double itemQuantity;
    private long itemId;

}
