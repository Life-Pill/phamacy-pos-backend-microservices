package com.lifepill.posorderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private long orderId;
    private long employerId;
    private long branchId;
    private Date orderDate;
    private double totalAmount;
    private GroupedOrderDetailsDTO groupedOrderDetails;
}