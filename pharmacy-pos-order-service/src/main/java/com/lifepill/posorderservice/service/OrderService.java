package com.lifepill.posorderservice.service;

import com.lifepill.posorderservice.dto.OrderResponseDTO;
import com.lifepill.posorderservice.dto.RequestOrderDetailsSaveDTO;
import com.lifepill.posorderservice.dto.RequestOrderSaveDTO;

import java.util.List;

public interface OrderService {

    String addOrder(RequestOrderSaveDTO requestOrderSaveDTO);

    List<OrderResponseDTO> getAllOrdersWithDetails();

    OrderResponseDTO getOrderWithDetailsById(long orderId);

    String updateOrderDetailsById(long orderId, List<RequestOrderDetailsSaveDTO> requestOrderDetailsSaveDTOList);
}
