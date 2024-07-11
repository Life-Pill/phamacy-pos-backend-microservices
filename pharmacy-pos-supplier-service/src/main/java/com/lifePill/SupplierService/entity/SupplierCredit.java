/*
package com.lifePill.SupplierService.entity;

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
@Table(name = "supplier_credit")
public class SupplierCredit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator= "native")
    @GenericGenerator(name = "native",strategy = "native")
    @Column(name = "supplier_credit_Id")
    private long supplierCreditId;
    @Column(name = "credit_amount")
    private double creditAmount;
    @Column(name = "credit_date")
    private Date creditDate;
    @Column(name = "payment_date")
    private Date paymentDate;
    @Column(name = "payment_amount")
    private double paymentAmount;
    @Column(name = "payment_completed_status")
    private boolean paymentCompletedStatus;

}
*/
