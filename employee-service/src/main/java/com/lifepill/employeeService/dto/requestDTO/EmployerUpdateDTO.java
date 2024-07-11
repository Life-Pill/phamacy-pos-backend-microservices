package com.lifepill.employeeService.dto.requestDTO;

import com.lifepill.employeeService.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Employer update dto.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployerUpdateDTO {
    private long employerId;
//    private int branchId;
    private String employerNicName;
    private String employerEmail;
    private String employerNic;
    private String employerPhone;
    private double employerSalary;
    private Role role;

}