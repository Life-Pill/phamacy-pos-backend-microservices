package com.lifePill.SupplierService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * The type Supplier company.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "supplierCompany")
public class SupplierCompany extends BaseEntity{

    @Id
    @Column(name = "company_id", length = 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long CompanyId;
    @Column(name = "company_name", length = 100)
    private String CompanyName;
    @Column(name = "company_address", length = 100)
    private String CompanyAddress;
    @Column(name = "company_contact", length = 12)
    private String CompanyContact;
    @Column(name = "company_email", length = 50)
    private String CompanyEmail;
    @Column(name = "company_description")
    private String CompanyDescription;
    @Column(name = "company_image")
    private String companyImage;
    @Column(name = "company_status")
    private boolean CompanyStatus;
    @Column(name = "company_rating")
    private long CompanyRating;
    @Column(name = "company_bank")
    private String CompanyBank;
    @Column(name = "company_account_number")
    private String CompanyAccountNumber;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplierCompany")
    private Set<Supplier> suppliers;
}
