package com.lifepill.posorderservice.controller;

import com.lifepill.posorderservice.dto.OrderResponseDTO;
import com.lifepill.posorderservice.dto.RequestOrderDetailsSaveDTO;
import com.lifepill.posorderservice.dto.RequestOrderSaveDTO;
import com.lifepill.posorderservice.service.OrderService;
import com.lifepill.posorderservice.util.StandardResponse;
import lombok.AllArgsConstructor;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controller class for handling order-related endpoints.
 */
@RestController
@RequestMapping("/lifepill/v1/order")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    /**
     * Saves an order.
     *
     * @param requestOrderSaveDTO The DTO containing order details.
     * @return A response entity indicating the result of the operation.
     */
    @PostMapping(path = "/save")
    public ResponseEntity<StandardResponse> saveItem(@RequestBody RequestOrderSaveDTO requestOrderSaveDTO) {
        String orderId =  orderService.addOrder(requestOrderSaveDTO);

        return new ResponseEntity<>(
                new StandardResponse(
                        201,
                        orderId +"Item Saved Successfully",
                        orderId
                ),
                HttpStatus.CREATED);
    }

    /**
     * Retrieves all orders in the system.
     *
     * @return A response entity containing a list of all orders.
     */
    @GetMapping("/get-all-orders-with-details")
    public ResponseEntity<StandardResponse> getAllOrdersWithDetails() {
        List<OrderResponseDTO> orderResponseDTOList = orderService.getAllOrdersWithDetails();

        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "All Orders Retrieved Successfully",
                        orderResponseDTOList
                ),
                HttpStatus.OK);
    }

    /**
     * Gets order with details by id.
     *
     * @param orderId the order id
     * @return the order with details by id
     */
    @GetMapping("/getOrderWithDetails/{orderId}")
    public ResponseEntity<StandardResponse> getOrderWithDetailsById(@PathVariable long orderId) {
        OrderResponseDTO orderResponseDTO = orderService.getOrderWithDetailsById(orderId);
        return new ResponseEntity<>(
                new StandardResponse(200, "Order Retrieved Successfully", orderResponseDTO),
                HttpStatus.OK);
    }

    /**
     * Updates order details by id.
     *
     * @param orderId the order id
     * @param requestOrderDetailsSaveDTOList the request order details save dto list
     * @return the response entity
     */
    //TODO: when updating inventory stock update and resolve and check if the stock is available not working properly
    @PutMapping("/updateOrderDetails/{orderId}")
    public ResponseEntity<StandardResponse> updateOrderDetailsById(
            @PathVariable long orderId,
            @RequestBody List<RequestOrderDetailsSaveDTO> requestOrderDetailsSaveDTOList
    ) {
        String message = orderService.updateOrderDetailsById(orderId, requestOrderDetailsSaveDTOList);
        return new ResponseEntity<>(
                new StandardResponse(200, message, requestOrderDetailsSaveDTOList),
                HttpStatus.OK);
    }

}
