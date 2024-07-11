package com.lifepill.authService.dto;

import com.lifepill.authService.entity.enums.Gender;
import com.lifepill.authService.entity.enums.Role;
import lombok.*;

import java.util.Date;

/**
 * The type Employer dto.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
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