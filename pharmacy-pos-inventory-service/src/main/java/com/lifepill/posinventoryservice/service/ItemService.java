package com.lifepill.posinventoryservice.service;

import com.lifepill.posinventoryservice.dto.ApiResponseDTO.SupplierItemApiResponseDTO;
import com.lifepill.posinventoryservice.dto.ItemCategoryDTO;
import com.lifepill.posinventoryservice.dto.ItemQuantityDTO;
import com.lifepill.posinventoryservice.dto.requestDTO.ItemSaveRequestCategoryDTO;
import com.lifepill.posinventoryservice.dto.requestDTO.ItemSaveRequestDTO;
import com.lifepill.posinventoryservice.dto.requestDTO.ItemUpdateDTO;
import com.lifepill.posinventoryservice.dto.responseDTO.ItemGetAllResponseDTO;
import com.lifepill.posinventoryservice.dto.responseDTO.ItemGetResponseDTO;
import com.lifepill.posinventoryservice.dto.responseDTO.ItemGetResponseWithoutSupplierDetailsDTO;

import java.util.List;
import java.util.Map;

public interface ItemService {

    String saveItems(ItemSaveRequestDTO itemSaveRequestDTO);

    String saveCategory(ItemCategoryDTO categoryDTO);

    List<ItemCategoryDTO> getAllCategories();

    String updateCategoryDetails(long categoryId, ItemCategoryDTO categoryDTO);

    String deleteCategory(long categoryId);

    List<ItemGetAllResponseDTO> getAllItems();

    List<ItemGetResponseDTO> getItemByName(String itemName);

    List<ItemGetResponseDTO> getItemByBarCode(String itemBarCode);

    List<ItemGetResponseDTO> getItemByStockStatus(boolean activeStatus);

    SupplierItemApiResponseDTO getAllDetailsItemById(long itemId);

    String deleteItem(long itemId);

    ItemGetResponseWithoutSupplierDetailsDTO getItemAndCategoryById(long itemId);

    String updateItem(ItemUpdateDTO itemUpdateDTO);

    String saveItemWithCategory(ItemSaveRequestCategoryDTO itemSaveRequestCategoryDTO);

    boolean checkItemExistsAndQuantityAvailable(long itemId, double requiredQuantity);

    void updateItemQuantities(List<ItemQuantityDTO> items);
}
