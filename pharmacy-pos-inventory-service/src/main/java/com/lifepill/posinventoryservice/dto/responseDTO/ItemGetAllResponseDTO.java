package com.lifepill.posinventoryservice.dto.responseDTO;


import com.lifepill.posinventoryservice.entity.enums.MeasuringUnitType;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ItemGetAllResponseDTO {
    private long itemId;
    private String itemName;
    private double sellingPrice;
    private String itemBarCode;
    private Date supplyDate;
    private double supplierPrice;
    private boolean isFreeIssued;
    private boolean isDiscounted;
    private String itemManufacture;
    private double itemQuantity;
    private String itemCategory;
    private boolean isStock;
    private MeasuringUnitType measuringUnitType;
    private Date manufactureDate;
    private Date expireDate;
    private Date purchaseDate;
    private String warrantyPeriod;
    private String rackNumber;
    private double discountedPrice;
    private double discountedPercentage;
    private String warehouseName;
    private boolean isSpecialCondition;
    private String itemImage;
    private String itemDescription;
}
