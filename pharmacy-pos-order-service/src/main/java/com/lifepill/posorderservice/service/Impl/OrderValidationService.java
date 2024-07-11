package com.lifepill.posorderservice.service.Impl;

import com.lifepill.posorderservice.dto.RequestOrderDetailsSaveDTO;
import com.lifepill.posorderservice.dto.RequestOrderSaveDTO;
import com.lifepill.posorderservice.dto.ItemQuantityDTO;
import com.lifepill.posorderservice.dto.RequestPaymentDetailsDTO;
import com.lifepill.posorderservice.entity.Order;
import com.lifepill.posorderservice.entity.PaymentDetails;
import com.lifepill.posorderservice.exception.InsufficientItemQuantityException;
import com.lifepill.posorderservice.exception.NotFoundException;
import com.lifepill.posorderservice.repository.PaymentRepository;
import com.lifepill.posorderservice.service.APIClient.APIClientBranchService;
import com.lifepill.posorderservice.service.APIClient.APIClientEmployeeService;
import com.lifepill.posorderservice.service.APIClient.APIClientInventoryService;
import com.lifepill.posorderservice.util.StandardResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class OrderValidationService {

    private ModelMapper modelMapper;
    private PaymentRepository paymentRepository;
    private APIClientEmployeeService apiClientEmployeeService;
    private APIClientBranchService apiClientBranchService;
    private APIClientInventoryService apiClientInventoryService;

    /**
     * Checks the stock availability of items in the order.
     *
     * @param requestOrderSaveDTO The DTO containing the order details.
     * @throws InsufficientItemQuantityException if an item in the order does not have enough quantity.
     * @throws NotFoundException                 if an item in the order is not found in the database.
     */
    public void checkItemStock(RequestOrderSaveDTO requestOrderSaveDTO) {
        List<RequestOrderDetailsSaveDTO> orderDetails = requestOrderSaveDTO.getOrderDetails();

        for (RequestOrderDetailsSaveDTO orderDetail : orderDetails) {

            ResponseEntity<StandardResponse> responseEntityForItem =
                    apiClientInventoryService.checkItemExistsAndQuantityAvailable(
                            orderDetail.getItemId(), orderDetail.getItemQuantity()
                    );

            boolean itemExists = (boolean) Objects.requireNonNull(responseEntityForItem.getBody()).getData();

            if (!itemExists) {
                throw new NotFoundException("Item not found with ID: " + orderDetail.getItemId());
            }

            ResponseEntity<StandardResponse> responseEntityForItemStock =
                    apiClientInventoryService.checkItemExistsAndQuantityAvailable(
                            orderDetail.getItemId(), orderDetail.getItemQuantity()
                    );

            boolean itemStockAvailable =
                    (boolean) Objects.requireNonNull(responseEntityForItemStock.getBody()).getData();

            if (!itemStockAvailable) {
                throw new InsufficientItemQuantityException(
                        "Insufficient quantity for item with ID: " + orderDetail.getItemId()
                );
            }
        }
    }

    /**
     * Checks if an employer exists by their ID.
     *
     * @param employerId The ID of the employer to check.
     * @throws NotFoundException if the employer is not found.
     */
    public void checkEmployerExists(long employerId) {
        ResponseEntity<StandardResponse> responseEntityForEmployee =
                apiClientEmployeeService.checkEmployerExistsById(employerId);

        boolean employerExists = (boolean) Objects.requireNonNull(responseEntityForEmployee.getBody()).getData();

        if(!employerExists){
            throw new NotFoundException("Employer not found with ID: " + employerId);
        }
    }

    /**
     * Checks if a branch exists by its ID.
     *
     * @param branchId The ID of the branch to check.
     * @throws NotFoundException if the branch is not found.
     */
    public void checkBranchExists(int branchId) {
        ResponseEntity<StandardResponse> responseEntityForBranch =
                apiClientBranchService.checkBranchExistsById(branchId);

        boolean branchExists = (boolean) Objects.requireNonNull(responseEntityForBranch.getBody()).getData();

        if (!branchExists) {
            throw new NotFoundException("Branch not found with ID: " + branchId);
        }
    }

    /**
     * Updates the quantities of items in the database after an order is placed.
     *
     * @param requestOrderSaveDTO The DTO containing the order details.
     * @throws NotFoundException if an item in the order is not found in the database.
     */
    public void updateItemQuantities(RequestOrderSaveDTO requestOrderSaveDTO) {
        List<RequestOrderDetailsSaveDTO> orderDetails = requestOrderSaveDTO.getOrderDetails();
        List<ItemQuantityDTO> itemsQuantityList = new ArrayList<>();

        for (RequestOrderDetailsSaveDTO orderDetail : orderDetails) {
            ItemQuantityDTO itemQuantityDTO = new ItemQuantityDTO();
            itemQuantityDTO.setItemId(orderDetail.getItemId());
            itemQuantityDTO.setItemQuantity(orderDetail.getItemQuantity());
            itemsQuantityList.add(itemQuantityDTO);
        }
        apiClientInventoryService.updateItemQuantities(itemsQuantityList);

    }

    /**
     * Saves payment details for an order.
     *
     * @param paymentDetailsDTO The DTO containing payment details.
     * @param order             The order for which the payment is made.
     */
    public void savePaymentDetails(RequestPaymentDetailsDTO paymentDetailsDTO, Order order) {

        PaymentDetails paymentDetails = modelMapper.map(paymentDetailsDTO, PaymentDetails.class);
        paymentDetails.setOrders(order);

        paymentRepository.save(paymentDetails);
    }

}