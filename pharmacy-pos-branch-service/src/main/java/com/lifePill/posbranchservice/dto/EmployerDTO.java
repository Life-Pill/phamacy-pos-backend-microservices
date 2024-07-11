package com.lifePill.posbranchservice.dto;

import com.lifePill.posbranchservice.entity.enums.Gender;
import com.lifePill.posbranchservice.entity.enums.Role;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmployerDTO {
    private long employerId;
    private long branchId;
    private String employerNicName;
    private String employerFirstName;
    private String employerLastName;
    private String employerPassword;
    private String employerEmail;
    private String employerPhone;
    private String employerAddress;
    private double employerSalary;
    private String employerNic;
    private boolean isActiveStatus;
    private Gender gender;
    private Date dateOfBirth;
    private Role role;
    private int pin;
    private byte[] profileImage;
}
