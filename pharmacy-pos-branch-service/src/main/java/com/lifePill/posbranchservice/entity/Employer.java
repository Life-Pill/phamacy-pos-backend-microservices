package com.lifePill.posbranchservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lifePill.posbranchservice.entity.enums.Gender;
import com.lifePill.posbranchservice.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;


/**
 * The type Employer.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employer")
@Builder
public class Employer extends BaseEntity {
    @Id
    @Column(name = "employer_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long employerId;

    @Column(name = "employer_nic_name", length = 50)
    private String employerNicName;

    @Column(name = "employer_first_name", nullable = false, length = 50)
    private String employerFirstName;

    @Column(name = "employer_last_name", length = 50)
    private String employerLastName;

    @Lob
    @Column(name = "profile_image",nullable = true)
    private byte[] profileImage;

    @Column(name = "employer_password", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String employerPassword;

    @Column(name = "employer_email", length = 50, unique = true)
    private String employerEmail;

    @Column(name = "employer_phone", length = 12)
    private String employerPhone;

    @Column(name = "employer_address", length = 100)
    private String employerAddress;

    @Column(name = "employer_sallary")
    private double employerSalary;

    @Column(name = "employer_nic", nullable = false, length = 12)
    private String employerNic;

    @Column(name = "is_active", columnDefinition = "BOOLEAN default false")
    private boolean isActiveStatus;

    @Column(name = "pin",length = 4)
    private int pin;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 10, nullable = false)
    private Gender gender;

    @Column(name = "employer_date_of_birth", columnDefinition = "DATE")
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "role",length = 15,nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "employer_bank_details_id", nullable = true)
    private EmployerBankDetails employerBankDetails;


}
