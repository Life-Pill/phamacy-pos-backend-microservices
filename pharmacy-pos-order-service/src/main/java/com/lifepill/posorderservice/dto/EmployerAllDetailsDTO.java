package com.lifepill.posorderservice.dto;

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
