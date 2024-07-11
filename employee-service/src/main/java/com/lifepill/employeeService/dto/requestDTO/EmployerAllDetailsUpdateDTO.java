package com.lifepill.employeeService.dto.requestDTO;

import com.lifepill.employeeService.entity.enums.Gender;
import com.lifepill.employeeService.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * The type Employer all details update dto.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployerAllDetailsUpdateDTO {
 //   private long cashierId;
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
    private Date dateOfBirth;
    private Role role;
    private int pin;

}