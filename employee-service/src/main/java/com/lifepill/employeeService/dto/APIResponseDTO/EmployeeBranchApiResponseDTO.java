package com.lifepill.employeeService.dto.APIResponseDTO;

import com.lifepill.employeeService.dto.BranchDTO;
import com.lifepill.employeeService.dto.EmployerDTO;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeBranchApiResponseDTO {
    private EmployerDTO employerDTO;
    private BranchDTO branchDTO;
}
