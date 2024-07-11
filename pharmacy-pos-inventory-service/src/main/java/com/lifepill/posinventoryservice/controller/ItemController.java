package com.lifepill.posinventoryservice.controller;

import com.lifepill.posinventoryservice.dto.ApiResponseDTO.SupplierItemApiResponseDTO;
import com.lifepill.posinventoryservice.dto.ItemQuantityDTO;
import com.lifepill.posinventoryservice.dto.requestDTO.ItemSaveRequestCategoryDTO;
import com.lifepill.posinventoryservice.dto.requestDTO.ItemSaveRequestDTO;
import com.lifepill.posinventoryservice.dto.requestDTO.ItemUpdateDTO;
import com.lifepill.posinventoryservice.dto.responseDTO.ItemGetAllResponseDTO;
import com.lifepill.posinventoryservice.dto.responseDTO.ItemGetResponseDTO;
import com.lifepill.posinventoryservice.dto.responseDTO.ItemGetResponseWithoutSupplierDetailsDTO;
import com.lifepill.posinventoryservice.service.ItemService;
import com.lifepill.posinventoryservice.util.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * Controller class for managing item-related operations.
 */
@RestController
@RequestMapping("/lifepill/v1/item")
@AllArgsConstructor
public class ItemController {

    private ItemService itemService;

  /**
     * Saves an item along with its category.
     *
     * @param itemSaveRequestCategoryDTO The DTO containing details of the item and its category to be saved.
     * @return ResponseEntity containing a StandardResponse object with a success message.
     */
    @PostMapping(path = "/save-item")
    public ResponseEntity<StandardResponse> saveItemWithCategory(
            @RequestBody ItemSaveRequestCategoryDTO itemSaveRequestCategoryDTO
    ) {
        String message = itemService.saveItemWithCategory(itemSaveRequestCategoryDTO);

        return new ResponseEntity<>(
                new StandardResponse(201, "Successfully Saved Item", message),
                HttpStatus.CREATED);
    }

    /**
     * Saves an item.
     *
     * @param itemSaveRequestDTO The DTO containing details of the item to be saved.
     * @return ResponseEntity containing a StandardResponse object with a success message.
     */
    //TODO: need to improvement don't use saveItems for this use save item
    @PostMapping(path = "/save")
    public ResponseEntity<StandardResponse> saveItem(@RequestBody ItemSaveRequestDTO itemSaveRequestDTO) {
        String message = itemService.saveItems(itemSaveRequestDTO);

        return new ResponseEntity<>(
                new StandardResponse(201, message, itemSaveRequestDTO),
                HttpStatus.CREATED);
    }

    /**
     * Retrieves all items.
     *
     * @return ResponseEntity containing a StandardResponse object with a list of all items.
     */
    @GetMapping(path = "get-all-items")
    public ResponseEntity<StandardResponse> getAllItems() {
        List<ItemGetAllResponseDTO> allItems = itemService.getAllItems();
        return new ResponseEntity<>(
                new StandardResponse(
                        201,
                        "Successfully retrieve all items",
                        allItems
                ),
                HttpStatus.OK
        );
    }

    /**
     * Retrieves items by name and stock.
     *
     * @param itemName The name of the item to retrieve.
     * @return List of ItemGetResponseDTO containing items with the specified name and stock.
     */
    @GetMapping(path = "/get-by-name", params = "itemName")
    public ResponseEntity<StandardResponse> getItemByNameAndStock(@RequestParam(value = "itemName") String itemName) {
        List<ItemGetResponseDTO> itemDTOS = itemService.getItemByName(itemName);
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Successfully retrieve items",
                        itemDTOS
                ),
                HttpStatus.OK
        );
    }

    /**
     * Retrieves items by barcode.
     *
     * @param itemBarCode The barcode of the item to retrieve.
     * @return List of ItemGetResponseDTO containing items with the specified barcode.
     */
    @GetMapping(path = "/get-by-barcode", params = "barcode")
    public ResponseEntity<StandardResponse> getItemByBarCode(@RequestParam(value = "barcode") String itemBarCode) {
        List<ItemGetResponseDTO> itemDTOS = itemService.getItemByBarCode(itemBarCode);
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Successfully retrieve items",
                        itemDTOS
                ),
                HttpStatus.OK
        );
    }

    /**
     * Retrieves all items by their active status.
     *
     * @param activeStatus The status indicating whether the items are active or not.
     * @return ResponseEntity containing a StandardResponse object with a list of items based on their active status.
     */
    @GetMapping(path = "/get-all-item-by-status", params = "activeStatus")
    public ResponseEntity<StandardResponse> getAllItemByActiveStatus(
            @RequestParam(value = "activeStatus") boolean activeStatus) {
        List<ItemGetResponseDTO> itemDTOS = itemService.getItemByStockStatus(activeStatus);

        return new ResponseEntity<>(
                new StandardResponse(200,
                        "Success", itemDTOS),
                HttpStatus.OK);
    }

    /**
     * Saves an item category.
     *
     * @return A message indicating the result of the save operation.
     */
    @GetMapping(path = "/get-item-all-details-by-id/{itemId}")
    public ResponseEntity<StandardResponse> getItemAllDetailsById(@PathVariable(value = "itemId") long itemId){
        SupplierItemApiResponseDTO supplierItemApiResponseDTO = itemService.getAllDetailsItemById(itemId);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",supplierItemApiResponseDTO),
                HttpStatus.OK
        );
    }

    /**
     * Deletes an item by its ID.
     *
     * @param itemId The ID of the item to be deleted.
     * @return A message indicating the result of the delete operation.
     */
    @DeleteMapping("/delete-item/{itemId}")
    public ResponseEntity<StandardResponse> deleteItem(@PathVariable(value = "itemId") int itemId) {
        String delete = itemService.deleteItem(itemId);
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        delete,
                        itemId
                ),
                HttpStatus.OK
        );
    }

    /**
     * Gets item with category by id.
     *
     * @param itemId the item id
     * @return the item with category by id
     */
    @GetMapping(path = "/get-item-with-category-details-by-id/{itemId}")
    public ResponseEntity<StandardResponse> getItemWithCategoryById(
            @PathVariable(value = "itemId") long itemId
    ){
        ItemGetResponseWithoutSupplierDetailsDTO itemGetResponsewithoutSupplierDetailsDTO =
                itemService.getItemAndCategoryById(itemId);
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "Success retrieve item with category by id:" + itemId ,
                        itemGetResponsewithoutSupplierDetailsDTO
                ),
                HttpStatus.OK
        );
    }

    /**
     * Updates an item.
     *
     * @param itemUpdateDTO The DTO containing updated item information.
     * @return A message indicating the result of the update operation.
     */
    @PutMapping("/update-item")
    public ResponseEntity<StandardResponse> updateItem(
            @RequestBody ItemUpdateDTO itemUpdateDTO
    ){
        String message = itemService.updateItem(itemUpdateDTO);
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        message,
                        itemUpdateDTO
                ),
                HttpStatus.OK
        );
    }

    /**
     * Checks if an item exists by their ID and if the required quantity is available in stock.
     *
     * @param itemId The ID of the item to check.
     * @param requiredQuantity The required quantity of the item.
     * @return ResponseEntity containing a StandardResponse object with a success message if the item exists and the required quantity is available, error message otherwise.
     */
    @GetMapping(path = "/check-item-stock/{itemId}/{requiredQuantity}")
    public ResponseEntity<StandardResponse> checkItemExistsAndQuantityAvailable(
            @PathVariable(value = "itemId") long itemId,
            @PathVariable(value = "requiredQuantity") double requiredQuantity
    ) {
        boolean isAvailable = itemService.checkItemExistsAndQuantityAvailable(itemId, requiredQuantity);
        if (isAvailable) {
            return new ResponseEntity<>(
                    new StandardResponse(
                            200,
                            "Item exists and required quantity is available",
                            true
                    ),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new StandardResponse(
                            404,
                            "Item does not exist or required quantity is not available",
                            false
                    ),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    /**
     * Reduces the quantity of a list of items in the stock.
     *
     * @param items A list of items with their IDs and quantities to be reduced.
     * @return ResponseEntity containing a StandardResponse object with a success message if the operation is successful, error message otherwise.
     */
    @PostMapping(path = "/update-item-quantities")
    public ResponseEntity<StandardResponse> updateItemQuantities(
            @RequestBody List<ItemQuantityDTO> items
    ) {
        try {
            itemService.updateItemQuantities(items);
            return new ResponseEntity<>(
                    new StandardResponse(
                            200,
                            "Item quantities update successfully",
                            items
                    ),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new StandardResponse(
                            500,
                            "An error occurred while reducing item quantities",
                            e.getMessage()
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


}
