/*
package com.lifePill.SupplierService.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.function.Suppliers;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "supplier_purchase_return")
public class SupplierPurchaseReturn {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator= "native")
    @GenericGenerator(name = "native",strategy = "native")
    @Column(name = "purchase_return_id")
    private String purchaseReturnId;
    @Column(name = "return_date")
    private String returnDate;
    @Column(name = "return_reason")
    private String returnReason;
    @Column(name = "return_quantity")
    private String returnQuantity;

    @ManyToOne
    @JoinColumn(name="supplier_id", nullable=false)
    private Suppliers suppliers;

    //TODO
   // productId
  //- supplierId : Int
}
*/
