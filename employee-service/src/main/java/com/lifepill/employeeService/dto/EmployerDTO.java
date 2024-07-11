package com.lifepill.employeeService.dto;

import com.lifepill.employeeService.entity.enums.Gender;
import com.lifepill.employeeService.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * The type Employer dto.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployerDTO {
    private long employerId;
    private int branchId;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    private Role role;
    private int pin;
    private byte[] profileImage;
    //private Order order;

}