package com.lifepill.authService.util.mappers;

import com.lifepill.authService.dto.EmployerBankDetailsDTO;
import com.lifepill.authService.entity.EmployerBankDetails;
import org.springframework.stereotype.Component;

@Component
public class EmployerMapper {


    public EmployerBankDetails mapBankDetailsDTOToEntity(EmployerBankDetailsDTO bankDetailsDTO) {
        if (bankDetailsDTO == null) {
            return null;
        }
        EmployerBankDetails employerBankDetails = new EmployerBankDetails();
        employerBankDetails.setBankName(bankDetailsDTO.getBankName());
        employerBankDetails.setBankBranchName(bankDetailsDTO.getBankBranchName());
        employerBankDetails.setBankAccountNumber(bankDetailsDTO.getBankAccountNumber());
        employerBankDetails.setEmployerDescription(bankDetailsDTO.getEmployerDescription());
        employerBankDetails.setMonthlyPayment(bankDetailsDTO.getMonthlyPayment());
        employerBankDetails.setMonthlyPaymentStatus(bankDetailsDTO.getMonthlyPaymentStatus());
        employerBankDetails.setEmployerId(bankDetailsDTO.getEmployerId());
        employerBankDetails.setEmployerBankDetailsId(bankDetailsDTO.getEmployerBankDetailsId());
      //  employerBankDetails.setEmployers(bankDetailsDTO.getEmployers());
        return employerBankDetails;
    }

}