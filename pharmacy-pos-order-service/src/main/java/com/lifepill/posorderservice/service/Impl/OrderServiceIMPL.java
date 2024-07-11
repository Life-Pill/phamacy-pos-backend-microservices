package com.lifepill.posorderservice.service.Impl;

import com.lifepill.posorderservice.dto.*;
import com.lifepill.posorderservice.entity.Order;
import com.lifepill.posorderservice.entity.OrderDetails;
import com.lifepill.posorderservice.entity.PaymentDetails;
import com.lifepill.posorderservice.repository.OrderDetailsRepository;
import com.lifepill.posorderservice.repository.OrderRepository;
import com.lifepill.posorderservice.repository.PaymentRepository;
import com.lifepill.posorderservice.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Implementation of the {@link OrderService} interface that handles order-related operations.
 */
@Service
@Transactional
@AllArgsConstructor
public class OrderServiceIMPL implements OrderService {

    private OrderRepository orderRepository;
    private ModelMapper modelMapper;
    private OrderDetailsRepository orderDetailsRepository;
    private PaymentRepository paymentRepository;
    private OrderValidationService orderValidationService;

    /**
     * Adds an order to the system.
     *
     * @param requestOrderSaveDTO The DTO containing order details.
     * @return A message indicating the result of the operation.
     */
    @Override
    public String addOrder(RequestOrderSaveDTO requestOrderSaveDTO) {
        // check if the employer exists
        orderValidationService.checkEmployerExists(requestOrderSaveDTO.getEmployerId());

        // check if the branch exists
        orderValidationService.checkBranchExists((int) requestOrderSaveDTO.getBranchId());

        // Check if items in the order have sufficient quantity
        orderValidationService.checkItemStock(requestOrderSaveDTO);

        // Update item quantities
        orderValidationService.updateItemQuantities(requestOrderSaveDTO);

        Order order = new Order();
        order.setEmployerId(requestOrderSaveDTO.getEmployerId());
        order.setOrderDate(requestOrderSaveDTO.getOrderDate());
        order.setTotalAmount(requestOrderSaveDTO.getTotalAmount());
        order.setBranchId(requestOrderSaveDTO.getBranchId());
        orderRepository.save(order);

        if (orderRepository.existsById(order.getOrderId())) {
            List<OrderDetails> orderDetails = modelMapper.
                    map(requestOrderSaveDTO.getOrderDetails(), new TypeToken<List<OrderDetails>>() {
                            }
                                    .getType()
                    );
            for (OrderDetails orderDetail : orderDetails) {
                orderDetail.setOrders(order);
            }
            if (!orderDetails.isEmpty()) {
                orderDetailsRepository.saveAll(orderDetails);
            }
            orderValidationService.savePaymentDetails(requestOrderSaveDTO.getPaymentDetails(), order);
            return "saved";
        }

        return "Order saved successfully";
    }


    /**
     * Fetches all orders with their details from the database.
     *
     * @return A list of OrderResponseDTO objects, each representing an order with its details.
     */
    @Override
    public List<OrderResponseDTO> getAllOrdersWithDetails() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDTO> orderResponseDTOList = new ArrayList<>();

        for (Order order : orders) {
            OrderResponseDTO orderResponseDTO = modelMapper.map(order, OrderResponseDTO.class);
            GroupedOrderDetailsDTO groupedOrderDetailsDTO = new GroupedOrderDetailsDTO();
                groupedOrderDetailsDTO.setOrderDetails(modelMapper.map(
                        order.getOrderDetails(),
                        new TypeToken<List<RequestOrderDetailsSaveDTO>>() {}
                                .getType()
                        )
                );
            // Fetch the payment details from the database by order ID
            PaymentDetails paymentDetails = paymentRepository.findByOrders(order);
            RequestPaymentDetailsDTO requestPaymentDetailsDTO = modelMapper
                    .map(paymentDetails, RequestPaymentDetailsDTO.class);
            groupedOrderDetailsDTO.setPaymentDetails(requestPaymentDetailsDTO);

            groupedOrderDetailsDTO.setOrderCount(order.getOrderDetails().size());
            orderResponseDTO.setGroupedOrderDetails(groupedOrderDetailsDTO);

            orderResponseDTOList.add(orderResponseDTO);
        }
        return orderResponseDTOList;
    }

    /**
     * Fetches an order with its details from the database by its ID.
     *
     * @param orderId The ID of the order to fetch.
     * @return An OrderResponseDTO object representing the order with its details,
     * or null if no order with the given ID exists.
     */
    @Override
    public OrderResponseDTO getOrderWithDetailsById(long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            OrderResponseDTO orderResponseDTO = modelMapper.map(order.get(), OrderResponseDTO.class);
            GroupedOrderDetailsDTO groupedOrderDetailsDTO = new GroupedOrderDetailsDTO();
            groupedOrderDetailsDTO.setOrderDetails(modelMapper.map(
                    order.get().getOrderDetails(),
                    new TypeToken<List<RequestOrderDetailsSaveDTO>>() {}
                            .getType()
                    )
            );
            // Fetch the payment details from the database by order ID
            PaymentDetails paymentDetails = paymentRepository.findByOrders(order.get());
            RequestPaymentDetailsDTO requestPaymentDetailsDTO = modelMapper
                    .map(paymentDetails, RequestPaymentDetailsDTO.class);
            groupedOrderDetailsDTO.setPaymentDetails(requestPaymentDetailsDTO);

            groupedOrderDetailsDTO.setOrderCount(order.get().getOrderDetails().size());
            orderResponseDTO.setGroupedOrderDetails(groupedOrderDetailsDTO);

            return orderResponseDTO;
        }
        return null;
    }

    @Override
    public String updateOrderDetailsById(
            long orderId,
            List<RequestOrderDetailsSaveDTO> requestOrderDetailsSaveDTOList
    ) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            List<OrderDetails> orderDetails = modelMapper.map(
                    requestOrderDetailsSaveDTOList,
                    new TypeToken<List<OrderDetails>>() {}
                            .getType()
                    );
            for (OrderDetails orderDetail : orderDetails) {
                orderDetail.setOrders(order.get());
            }
            if (!orderDetails.isEmpty()) {
                orderDetailsRepository.saveAll(orderDetails);
            }
            return "Order details updated successfully";
        }
        return "Order details not updated";
    }

}