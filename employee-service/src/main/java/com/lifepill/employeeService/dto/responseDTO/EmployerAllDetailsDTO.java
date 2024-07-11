package com.lifepill.employeeService.dto.responseDTO;

import com.lifepill.employeeService.dto.EmployerBankDetailsDTO;
import com.lifepill.employeeService.dto.EmployerDTO;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmployerAllDetailsDTO {
    private EmployerDTO employerDTO;
    private EmployerBankDetailsDTO employerBankDetailsDTO;

}
