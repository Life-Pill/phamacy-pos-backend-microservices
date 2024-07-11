package com.lifepill.posorderservice.service.APIClient;

import com.lifepill.posorderservice.dto.ItemQuantityDTO;
import com.lifepill.posorderservice.util.StandardResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "INVENTORY-SERVICE/lifepill/v1")
public interface APIClientInventoryService {

    @GetMapping(path = "item/check-item-stock/{itemId}/{requiredQuantity}")
    ResponseEntity<StandardResponse> checkItemExistsAndQuantityAvailable(
            @PathVariable(value = "itemId") long itemId,
            @PathVariable(value = "requiredQuantity") double requiredQuantity
    );

    @PostMapping(path = "item/update-item-quantities")
    ResponseEntity<StandardResponse> updateItemQuantities(
            @RequestBody List<ItemQuantityDTO> items
    );
}
