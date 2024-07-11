/*
package com.lifePill.SupplierService.entity;

import com.lifePill.SupplierService.entity.enums.MeasuringUnitType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "supplier_product_details")
public class SupplierProductDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator= "native")
    @GenericGenerator(name = "native",strategy = "native")
    @Column(name = "supplier_details_Id")
    private long supplierDetailsId;
    @Column(name = "product_bar_code")
    private String ProductBarCode;
    @Column(name = "product_qr_code")
    private String ProductQRCode;
    @Column(name = "product_description")
    private String productDescription;
    @Column(name = "expiry_date")
    private Date expiryDate;
    @Column(name = "discount")
    private double discount;
    @Column(name = "discount_effect_from")
    private Date discountEffectFrom;
    @Column(name = "discount_effect_to")
    private Date discountEffectTo;
    @Column(name = "purchase_quantity")
    private String purchaseQuantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "measuring_unit_type", length = 20)
    private MeasuringUnitType measuringUnitType;


// TODO
    //connect supplier id and product details
//- supplier: String

    // TODO
    // connect supplier product item to real product item




}
*/
