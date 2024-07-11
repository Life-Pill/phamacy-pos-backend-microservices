package com.lifepill.posorderservice.entity;

import com.fasterxml.jackson.annotation.JsonTypeId;
import jakarta.persistence.*;
import lombok.*;

/**
 * The type Order details.
 */
@Entity
@Table(name = "order_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetails extends BaseEntity{
    @Id
    @Column(name = "order_details_id",length = 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderDetailsId;

    @Column(name = "item_name",length = 100,nullable = false)
    private String itemName;

    @Column(name = "item_quantity",length = 100,nullable = false)
    private Double itemQuantity;

    @Column(name = "item_id",nullable = false)
    private long itemId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order orders;


}