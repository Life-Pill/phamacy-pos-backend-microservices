package com.lifepill.posorderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupedOrderDetailsDTO {
    private List<RequestOrderDetailsSaveDTO> orderDetails;
    private RequestPaymentDetailsDTO paymentDetails;
    private int orderCount;
}