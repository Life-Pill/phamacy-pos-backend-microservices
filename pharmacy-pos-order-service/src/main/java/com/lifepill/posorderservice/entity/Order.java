package com.lifepill.posorderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;


/**
 * The type Order.
 */
@Entity
@Table(name = "orders")
@AllArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
public class Order extends BaseEntity {
    @Id
    @Column(name = "order_id", length = 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderId;

    @Column(name = "employer_id",nullable = false)
    private long employerId;

    @Column(name = "branch_id",nullable = false)
    private long branchId;

    @Column(name = "order_date", columnDefinition = "TIMESTAMP")
    private Date orderDate;

    @Column(name = "total_amount",nullable = false)
    private Double totalAmount;

    @OneToMany(mappedBy = "orders")
    private Set<OrderDetails> orderDetails;

    @OneToMany(mappedBy = "orders")
    private Set<PaymentDetails> paymentDetails;

}