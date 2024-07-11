package com.lifepill.posinventoryservice.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemQuantityDTO {
    private long itemId;
    private double itemQuantity;
}
