package com.lifepill.posorderservice.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lifepill.posorderservice.dto.BranchDTO;
import com.lifepill.posorderservice.dto.EmployerAllDetailsDTO;
import com.lifepill.posorderservice.dto.PharmacyBranchResponseDTO;
import com.lifepill.posorderservice.entity.Order;
import com.lifepill.posorderservice.repository.OrderRepository;
import com.lifepill.posorderservice.service.APIClient.APIClientBranchService;
import com.lifepill.posorderservice.service.APIClient.APIClientEmployeeService;
import com.lifepill.posorderservice.service.BranchSummaryService;
import com.lifepill.posorderservice.util.StandardResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementation of the BranchSummaryService interface.
 */
@Service
@AllArgsConstructor
public class BranchSummaryServiceIMPL implements BranchSummaryService {

    private OrderRepository orderRepository;
    private APIClientBranchService apiClientBranchService;
    private APIClientEmployeeService apiClientEmployeeService;
    private ModelMapper modelMapper;

    /**
     * Retrieves all branches with their sales information.
     *
     * @return List of PharmacyBranchResponseDTO containing sales information for each branch.
     */
    //TODO: Check sales response
    @Override
    public List<PharmacyBranchResponseDTO> getAllBranchesWithSales() {

        // Fetch all orders from the repository
        List<Order> allOrders = orderRepository.findAll();

        // Group orders by branchId and calculate total sales and count of orders for each branch
        Map<Long, Double> branchSalesMap = allOrders
                .stream()
                .collect(Collectors.groupingBy(
                        Order::getBranchId, Collectors.summingDouble(Order::getTotalAmount))
                );

        Map<Long, Long> branchOrdersCountMap = allOrders
                .stream()
                .collect(Collectors.groupingBy(
                        Order::getBranchId, Collectors.counting())
                );

        // For each branch, fetch manager and branch details, and create a PharmacyBranchResponseDTO
        return branchSalesMap.entrySet().stream().map(entry -> {
            Long branchId = entry.getKey();
            Double sales = entry.getValue();
            Integer orders = branchOrdersCountMap.getOrDefault(branchId, 0L).intValue();


            // fetch manager details based on branchId
            ResponseEntity<StandardResponse> managerResponse =
                    apiClientEmployeeService.getManagerByBranchId(branchId.intValue());

            ObjectMapper objectMapper = new ObjectMapper();
            EmployerAllDetailsDTO employerAllDetailsDTO = objectMapper.convertValue(
                    Objects.requireNonNull(managerResponse.getBody()).getData(), EmployerAllDetailsDTO.class
            );

            // fetch branch details based on branchId
            ResponseEntity<StandardResponse> branchResponse =
                    apiClientBranchService.getBranchById(branchId.intValue());
            BranchDTO branchDTO = modelMapper.map(
                    Objects.requireNonNull(branchResponse.getBody()).getData(), BranchDTO.class
            );

            return new PharmacyBranchResponseDTO(
                    sales,
                    orders,
                    employerAllDetailsDTO.getEmployerDTO().getEmployerFirstName(),
                    branchDTO
            );

        }).collect(Collectors.toList());

    }
}
