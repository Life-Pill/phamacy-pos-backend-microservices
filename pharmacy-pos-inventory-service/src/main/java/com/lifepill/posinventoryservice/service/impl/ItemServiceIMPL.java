package com.lifepill.posinventoryservice.service.impl;

import com.lifepill.posinventoryservice.dto.ApiResponseDTO.SupplierItemApiResponseDTO;
import com.lifepill.posinventoryservice.dto.ItemCategoryDTO;
import com.lifepill.posinventoryservice.dto.ItemQuantityDTO;
import com.lifepill.posinventoryservice.dto.SupplierAndSupplierCompanyDTO;
import com.lifepill.posinventoryservice.dto.requestDTO.ItemSaveRequestCategoryDTO;
import com.lifepill.posinventoryservice.dto.requestDTO.ItemSaveRequestDTO;
import com.lifepill.posinventoryservice.dto.requestDTO.ItemUpdateDTO;
import com.lifepill.posinventoryservice.dto.responseDTO.ItemGetAllResponseDTO;
import com.lifepill.posinventoryservice.dto.responseDTO.ItemGetIdResponseDTO;
import com.lifepill.posinventoryservice.dto.responseDTO.ItemGetResponseDTO;
import com.lifepill.posinventoryservice.dto.responseDTO.ItemGetResponseWithoutSupplierDetailsDTO;
import com.lifepill.posinventoryservice.entity.Item;
import com.lifepill.posinventoryservice.entity.ItemCategory;
import com.lifepill.posinventoryservice.exception.EntityDuplicationException;
import com.lifepill.posinventoryservice.exception.InsufficientItemQuantityException;
import com.lifepill.posinventoryservice.exception.NotFoundException;
import com.lifepill.posinventoryservice.repository.ItemCategoryRepository;
import com.lifepill.posinventoryservice.repository.ItemRepository;
import com.lifepill.posinventoryservice.service.APIClient.APIClientSupplierService;
import com.lifepill.posinventoryservice.service.APIClient.APIClientBranchService;
import com.lifepill.posinventoryservice.service.ItemService;
import com.lifepill.posinventoryservice.util.StandardResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemServiceIMPL implements ItemService {

    private ItemRepository itemRepository;
    private ModelMapper modelMapper;
    private ItemCategoryRepository itemCategoryRepository;
    private APIClientSupplierService apiClientSupplierService;
    private APIClientBranchService apiClientBranchService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemServiceIMPL.class);

    /**
     * Saves a new item based on the provided item save request DTO.
     *
     * @param itemSaveRequestDTO The DTO containing the details of the item to be saved.
     * @return A message indicating the success of the save operation.
     * @throws EntityDuplicationException If an item with the same ID already exists.
     */
    @Override
    public String saveItems(ItemSaveRequestDTO itemSaveRequestDTO) {
        Item item = modelMapper.map(itemSaveRequestDTO, Item.class);

        // Check if the item category exists
        ItemCategory category = itemCategoryRepository.findById(itemSaveRequestDTO.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found with ID: "
                        + itemSaveRequestDTO.getCategoryId())
                );

        item.setItemCategory(category); // Ensure item category is set

        // Check if branch exists
        ResponseEntity<StandardResponse> responseEntityForBranch =
                apiClientBranchService.checkBranchExistsById((int) itemSaveRequestDTO.getBranchId());

        boolean branchExists = (boolean) Objects.requireNonNull(responseEntityForBranch.getBody()).getData();

        if (branchExists) {
            item.setBranchId(itemSaveRequestDTO.getBranchId());
        } else {
            throw new NotFoundException("Branch not found with ID: " + itemSaveRequestDTO.getBranchId());
        }

        // Check if supplier exists
        ResponseEntity<StandardResponse> responseEntityForSupplier =
                apiClientSupplierService.checkSupplierExistsById(itemSaveRequestDTO.getSupplierId());

        boolean supplierExists = (boolean) Objects.requireNonNull(responseEntityForSupplier.getBody()).getData();

        if (supplierExists) {
            item.setSupplierId(itemSaveRequestDTO.getSupplierId());
        } else {
            throw new NotFoundException("Supplier not found with ID: "
                    + itemSaveRequestDTO.getSupplierId()
            );
        }

        if (!itemRepository.existsById(item.getItemId())) {
            itemRepository.save(item);
            return item.getItemName() + " Saved Successfully";
        } else {
            throw new EntityDuplicationException("Already added this Id item");
        }
    }

    /**
     * Retrieves all items from the database.
     *
     * @return A list of DTOs representing all items.
     * @throws NotFoundException If no items are found, or they are out of stock.
     */
    @Override
    public List<ItemGetAllResponseDTO> getAllItems() {
        List<Item> getAllItems = itemRepository.findAll();

        if (!getAllItems.isEmpty()) {
            List<ItemGetAllResponseDTO> itemGetAllResponseDTOSList = new ArrayList<>();
            for (Item item : getAllItems) {
                ItemGetAllResponseDTO itemGetAllResponseDTO = modelMapper.map(item, ItemGetAllResponseDTO.class);
                itemGetAllResponseDTOSList.add(itemGetAllResponseDTO);
            }
            return itemGetAllResponseDTOSList;
        } else {
            throw new NotFoundException("No Item Find or OUT of Stock");
        }
    }

    /**
     * Retrieves items by name and stock status.
     *
     * @param itemName The name of the item to search for.
     * @return A list of DTOs representing items matching the search criteria.
     * @throws NotFoundException If no items are found.
     */
    @Override
    public List<ItemGetResponseDTO> getItemByName(String itemName) {
        List<Item> items = itemRepository.findAllByItemName(itemName);
        if (!items.isEmpty()) {
            return modelMapper.map(
                    items,
                    new TypeToken<List<ItemGetResponseDTO>>() {
                    }.getType()
            );
        } else {
            throw new NotFoundException("Not found");
        }
    }

    /**
     * Retrieves items by barcode.
     *
     * @param itemBarCode The barcode of the item to search for.
     * @return A list of DTOs representing items with the specified barcode.
     * @throws NotFoundException If no items are found.
     */
    @Override
    public List<ItemGetResponseDTO> getItemByBarCode(String itemBarCode) {
        List<Item> item = itemRepository.findAllByItemBarCodeEquals(itemBarCode);
        if (!item.isEmpty()) {
            return modelMapper.map(
                    item,
                    new TypeToken<List<ItemGetResponseDTO>>() {
                    }.getType()
            );
        } else {
            throw new NotFoundException("No any item found for that barcode");
        }
    }

    /**
     * Retrieves items by stock status.
     *
     * @param activeStatus The stock status to filter by.
     * @return A list of DTOs representing items with the specified stock status.
     * @throws NotFoundException If no items are found.
     */
    @Override
    public List<ItemGetResponseDTO> getItemByStockStatus(boolean activeStatus) {
        List<Item> item = itemRepository.findAllByStockEquals(activeStatus);
        if (!item.isEmpty()) {
            List<ItemGetResponseDTO> itemGetResponseDTOS;
            itemGetResponseDTOS = modelMapper.map(
                    item,
                    new TypeToken<List<ItemGetResponseDTO>>() {
                    }.getType()
            );

            return itemGetResponseDTOS;

        } else {
            throw new NotFoundException("out of Stock");
        }
    }

    /**
     * Deletes an item with the specified ID.
     *
     * @param itemId The ID of the item to be deleted.
     * @return A message indicating the success of the delete operation.
     * @throws NotFoundException If the item to be deleted is not found.
     */
    @Override
    public String deleteItem(long itemId) {
        if (itemRepository.existsById(itemId)) {
            itemRepository.deleteById(itemId);

            return "deleted successfully: " + itemId;
        } else {
            throw new NotFoundException("No item found for that id: "+ itemId);
        }
    }

    /**
     * Retrieves all details of an item by its ID using a circuit breaker pattern.
     * The circuit breaker pattern is used to prevent system failure and maintain system stability when calling external services.
     * If the call to the external service fails, the circuit breaker goes into the open state and the fallback method is called.
     *
     * @param itemId The ID of the item whose details are to be retrieved.
     * @return A SupplierItemApiResponseDTO object containing all details of the item.
     * @throws NotFoundException If no item is found with the given ID.
     */
    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getAllDetailsItemByIdFallback")
    @Override
    public SupplierItemApiResponseDTO getAllDetailsItemById(long itemId) {
        LOGGER.info("Inside getAllDetailsItemById method of ItemServiceIMPL");
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item not found with ID: " + itemId));

        ItemGetIdResponseDTO itemGetIdResponseDTO = modelMapper.map(item, ItemGetIdResponseDTO.class);

        // Map Item Get All response
        ItemGetAllResponseDTO itemGetAllResponseDTO = modelMapper.map(item, ItemGetAllResponseDTO.class);
        itemGetIdResponseDTO.setItemGetAllResponseDTO(itemGetAllResponseDTO);

        // Map ItemCategory
        ItemCategory itemCategory = item.getItemCategory();
        ItemCategoryDTO itemCategoryDTO = modelMapper.map(itemCategory, ItemCategoryDTO.class);
        itemGetIdResponseDTO.setItemCategoryDTO(itemCategoryDTO);

        SupplierAndSupplierCompanyDTO supplierAndSupplierCompanyDTO =
                apiClientSupplierService.getSupplierAndCompanyBySupplierId(item.getSupplierId());

        SupplierItemApiResponseDTO supplierItemApiResponseDTO = new SupplierItemApiResponseDTO();
        supplierItemApiResponseDTO.setItemGetIdResponseDTO(itemGetIdResponseDTO);
        supplierItemApiResponseDTO.setSupplierAndSupplierCompanyDTO(supplierAndSupplierCompanyDTO);

        return supplierItemApiResponseDTO;
    }

    /**
     * Fallback method for getAllDetailsItemById.
     * This method is called when the circuit breaker is open and the call to the external service fails.
     * It returns a SupplierItemApiResponseDTO with default values.
     *
     * @param itemId The ID of the item whose details were to be retrieved.
     * @param exception The exception that caused the circuit breaker to open.
     * @return A SupplierItemApiResponseDTO with default values.
     */
    public SupplierItemApiResponseDTO getAllDetailsItemByIdFallback(long itemId, Exception exception) {
        LOGGER.error("Inside getAllDetailsItemByIdFallback method of ItemServiceIMPL");
        LOGGER.error("Exception is: ", exception);

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item not found with ID: " + itemId));

        ItemGetIdResponseDTO itemGetIdResponseDTO = modelMapper.map(item, ItemGetIdResponseDTO.class);

        // Map Item Get All response
        ItemGetAllResponseDTO itemGetAllResponseDTO = modelMapper.map(item, ItemGetAllResponseDTO.class);
        itemGetIdResponseDTO.setItemGetAllResponseDTO(itemGetAllResponseDTO);

        // Map ItemCategory
        ItemCategory itemCategory = item.getItemCategory();
        ItemCategoryDTO itemCategoryDTO = modelMapper.map(itemCategory, ItemCategoryDTO.class);
        itemGetIdResponseDTO.setItemCategoryDTO(itemCategoryDTO);

        SupplierItemApiResponseDTO supplierItemApiResponseDTO = new SupplierItemApiResponseDTO();
        supplierItemApiResponseDTO.setItemGetIdResponseDTO(itemGetIdResponseDTO);

        return supplierItemApiResponseDTO;
    }

    /**
     * Retrieves an item and its associated category by the item's ID.
     *
     * @param itemId The ID of the item whose details are to be retrieved.
     * @return An ItemGetResponseWithoutSupplierDetailsDTO object containing the item and its associated category.
     * @throws NotFoundException If no item is found with the given ID.
     */
    @Override
    public ItemGetResponseWithoutSupplierDetailsDTO getItemAndCategoryById(long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item not found with ID: " + itemId));

        ItemGetResponseWithoutSupplierDetailsDTO itemGetResponsewithoutSupplierDetailsDTO =
                modelMapper.map(item, ItemGetResponseWithoutSupplierDetailsDTO.class);

        // Map Get All Item Response
        ItemGetAllResponseDTO itemGetAllResponseDTO = modelMapper.map(item, ItemGetAllResponseDTO.class);
        itemGetResponsewithoutSupplierDetailsDTO.setItemGetAllResponseDTO(itemGetAllResponseDTO);

        // Map ItemCategory
        ItemCategory itemCategory = item.getItemCategory();
        ItemCategoryDTO itemCategoryDTO = modelMapper.map(itemCategory, ItemCategoryDTO.class);
        itemGetResponsewithoutSupplierDetailsDTO.setItemCategoryDTO(itemCategoryDTO);

        return itemGetResponsewithoutSupplierDetailsDTO;
    }

    //TODO: Need to Check all condition and optimize the code
    /**
     * Updates an existing item based on the provided update DTO.
     *
     * @param itemUpdateDTO The DTO containing the updated details of the item.
     * @return A message indicating the success of the update operation.
     * @throws NotFoundException If the item to be updated is not found.
     */
    @Override
    public String updateItem(ItemUpdateDTO itemUpdateDTO) {
        if (itemRepository.existsById((long) itemUpdateDTO.getItemId())) {
            Item item = itemRepository.getReferenceById((long) itemUpdateDTO.getItemId());
            item.setItemName(itemUpdateDTO.getItemName());
            item.setItemBarCode(itemUpdateDTO.getItemBarCode());
            item.setSupplyDate(itemUpdateDTO.getSupplyDate());
            item.setFreeIssued(itemUpdateDTO.isFreeIssued());
            item.setDiscounted(itemUpdateDTO.isDiscounted());
            item.setItemManufacture(itemUpdateDTO.getItemManufacture());
            item.setItemQuantity(itemUpdateDTO.getItemQuantity());
//            item.setItemCategory(itemUpdateDTO.getItemCategory());
            item.setStock(itemUpdateDTO.isStock());
            item.setMeasuringUnitType(itemUpdateDTO.getMeasuringUnitType());
            item.setManufactureDate(itemUpdateDTO.getManufactureDate());
            item.setExpireDate(itemUpdateDTO.getExpireDate());
            item.setPurchaseDate(itemUpdateDTO.getPurchaseDate());
            item.setWarrantyPeriod(itemUpdateDTO.getWarrantyPeriod());
            item.setRackNumber(itemUpdateDTO.getRackNumber());
            item.setDiscountedPrice(itemUpdateDTO.getDiscountedPrice());
            item.setDiscountedPercentage(itemUpdateDTO.getDiscountedPercentage());
            item.setWarehouseName(itemUpdateDTO.getWarehouseName());
            item.setSpecialCondition(itemUpdateDTO.isSpecialCondition());
            item.setItemImage(itemUpdateDTO.getItemImage());
            item.setItemDescription(itemUpdateDTO.getItemDescription());

            itemRepository.save(item);

            System.out.println(item);

            return "UPDATED ITEMS";
        } else {
            throw new NotFoundException("no Item found in that date");
        }
    }
    /**
     * Saves an item with its associated category and supplier.
     *
     * @param itemSaveRequestCategoryDTO The DTO containing the details of the item and its associated category and supplier.
     * @return A message indicating the success of the save operation.
     * @throws NotFoundException If the associated category or supplier is not found.
     */
    //TODO: need to check all conditions and optimize the code
    @Override
    public String saveItemWithCategory(ItemSaveRequestCategoryDTO itemSaveRequestCategoryDTO) {
        // Check if category exists
        ItemCategory category = itemCategoryRepository.findById(itemSaveRequestCategoryDTO.getCategoryId())
                .orElseGet(() -> {

                    // If category doesn't exist, create a new one
                    ItemCategory newCategory = new ItemCategory();
                    // Set category properties if needed
                    // newCategory.setCategoryName(itemSaveRequestCategoryDTO.getCategoryName());
                    // newCategory.setCategoryDescription(itemSaveRequestCategoryDTO.getCategoryDescription());
                    // Save the new category
                    return itemCategoryRepository.save(newCategory);
                });

        // Check if supplier exists
        ResponseEntity<StandardResponse> responseEntityForSupplier =
                apiClientSupplierService.checkSupplierExistsById(
                        itemSaveRequestCategoryDTO.getSupplierId()
                );
        if (responseEntityForSupplier.getBody() == null) {
            throw new NotFoundException("Supplier not found with ID: "
                    + itemSaveRequestCategoryDTO.getSupplierId());
        }

        // Check if branch exists
        ResponseEntity<StandardResponse> responseEntityForBranch =
                apiClientBranchService.checkBranchExistsById(
                        (int) itemSaveRequestCategoryDTO.getBranchId()
                );
        //TODO: Check if the Branch Id is null
        if (responseEntityForBranch.getBody() == null) {
            throw new NotFoundException("Branch not found with ID: "
                    + itemSaveRequestCategoryDTO.getBranchId());
        }

        itemRepository.findById(itemSaveRequestCategoryDTO.getItemId())
                .ifPresent(item -> {
                    throw new EntityDuplicationException("Item already exists with ID: "
                            + itemSaveRequestCategoryDTO.getItemId());
                });

        // Now, associate the item with the category and supplier
        Item item = modelMapper.map(itemSaveRequestCategoryDTO, Item.class);
        item.setItemCategory(category);
        //TODO: ensure supplier is set
        // item.setSupplier(supplier);
        // Ensure the supplier is set
        item.setBranchId(itemSaveRequestCategoryDTO.getBranchId());

        itemRepository.save(item);
        return "Item saved successfully with category and supplier";

        //TODO: Need to get response of real item id now it shows in zero
    }

    /**
     * Saves a new item category.
     *
     * @param categoryDTO The DTO containing the details of the category to be saved.
     * @return A message indicating the success of the save operation.
     */
    @Override
    public String saveCategory(ItemCategoryDTO categoryDTO) {
        // Convert DTO to entity and save the category
        ItemCategory category = modelMapper.map(categoryDTO, ItemCategory.class);
        itemCategoryRepository.save(category);
        return "Category saved successfully";
    }

    /**
     * Retrieves all item categories.
     *
     * @return A list of DTOs representing all item categories.
     * @throws NotFoundException If no categories are found.
     */
    @Override
    public List<ItemCategoryDTO> getAllCategories() {
        List<ItemCategory> categories = itemCategoryRepository.findAll();
        if (!categories.isEmpty()) {
            return categories.stream()
                    .map(category -> modelMapper.map(category, ItemCategoryDTO.class))
                    .collect(Collectors.toList());
        } else {
            throw new NotFoundException("No categories found");
        }
    }

    /**
     * Updates the details of an existing item category.
     *
     * @param categoryId  The ID of the category to be updated.
     * @param categoryDTO The DTO containing the updated details of the category.
     * @return A message indicating the success of the update operation.
     * @throws NotFoundException If the category to be updated is not found.
     */
    @Override
    public String updateCategoryDetails(long categoryId, ItemCategoryDTO categoryDTO) {
        // Check if the category exists
        Optional<ItemCategory> optionalCategory = itemCategoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            ItemCategory category = optionalCategory.get();

            // Update category details
            category.setCategoryName(categoryDTO.getCategoryName());
            category.setCategoryDescription(categoryDTO.getCategoryDescription());
            category.setCategoryImage(categoryDTO.getCategoryImage());

            // Save the updated category
            itemCategoryRepository.save(category);

            return "Category details updated successfully";
        } else {
            throw new NotFoundException("Category not found");
        }
    }

    /**
     * Deletes a category with the specified ID.
     *
     * @param categoryId The ID of the category to be deleted.
     * @return A message indicating the success of the delete operation.
     * @throws NotFoundException If the category to be deleted is not found.
     */
    @Override
    public String deleteCategory(long categoryId) {
        // Check if the category exists
        Optional<ItemCategory> optionalCategory = itemCategoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            ItemCategory category = optionalCategory.get();

            // Check if there are any items associated with this category
            if (!category.getItems().isEmpty()) {
                throw new IllegalStateException("Cannot delete category with associated items");
            }

            // Delete the category
            itemCategoryRepository.delete(category);

            return "Category deleted successfully";
        } else {
            throw new NotFoundException("Category not found");
        }
    }

    /**
     * Checks if an item exists by their ID and if the required quantity is available in stock.
     *
     * @param itemId The ID of the item to check.
     * @param requiredQuantity The required quantity of the item.
     * @return true if the item exists and the required quantity is available, false otherwise.
     * @throws NotFoundException if the item is not found.
     * @throws InsufficientItemQuantityException if the required quantity is not available.
     */
    public boolean checkItemExistsAndQuantityAvailable(long itemId, double requiredQuantity) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            if (item.getItemQuantity() >= requiredQuantity) {
                return true;
            } else {
                throw new InsufficientItemQuantityException(
                        "Item " + item.getItemId()
                                + " does not have enough quantity"
                );
            }
        } else {
            throw new NotFoundException("Item not found with ID: " + itemId);
        }
    }

    /**
     * Reduces the quantity of a list of items in the stock.
     *
     * @param items A list of items with their IDs and quantities to be reduced.
     * @throws NotFoundException if an item in the list is not found in the database.
     * @throws InsufficientItemQuantityException if an item in the list does not have enough quantity.
     */
    @Override
    public void updateItemQuantities(List<ItemQuantityDTO> items) {
        for (ItemQuantityDTO item : items) {
            long itemId = item.getItemId();
            double quantityToReduce = item.getItemQuantity();
            Optional<Item> optionalItem = itemRepository.findById(itemId);
            if (optionalItem.isPresent()) {
                Item itemInStock = optionalItem.get();
                if (itemInStock.getItemQuantity() >= quantityToReduce) {
                    itemInStock.setItemQuantity(itemInStock.getItemQuantity() - quantityToReduce);
                    itemRepository.save(itemInStock);
                } else {
                    throw new InsufficientItemQuantityException(
                            "Insufficient quantity for item with ID: " + itemId
                    );
                }
            } else {
                throw new NotFoundException("Item not found with ID: " + itemId);
            }
        }
    }

}