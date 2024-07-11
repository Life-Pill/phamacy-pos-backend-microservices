package com.lifepill.authService.dto.requestDTO.EmployerUpdate;

import com.lifepill.authService.entity.enums.Role;
import lombok.*;

/**
 * The type Employer update dto.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployerUpdateDTO {
    private long employerId;
    private int branchId;
    private String employerNicName;
    private String employerEmail;
    private String employerNic;
    private String employerPhone;
    private double employerSalary;
    private Role role;

}