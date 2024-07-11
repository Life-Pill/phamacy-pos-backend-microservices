package com.lifepill.employeeService.util.mappers;

import com.lifepill.employeeService.dto.EmployerBankDetailsDTO;
import com.lifepill.employeeService.dto.EmployerDTO;
import com.lifepill.employeeService.dto.responseDTO.EmployerAllDetailsDTO;
import com.lifepill.employeeService.entity.Employer;
import com.lifepill.employeeService.entity.EmployerBankDetails;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for mapping between Employer and EmployerDTO objects.
 * It uses the ModelMapper library to perform the mapping.
 */
@Component
public class EmployerMapper {

    private final ModelMapper modelMapper;

    public EmployerMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * This method is used to convert an Employer object to an EmployerAllDetailsDTO object.
     * It first maps the Employer object to an EmployerDTO object.
     * Then, if the Employer object has associated bank details, it maps those to an EmployerBankDetailsDTO object.
     * Finally, it creates a new EmployerAllDetailsDTO object using the EmployerDTO and EmployerBankDetailsDTO objects.
     *
     * @param employer The Employer object to be converted.
     * @return The converted EmployerAllDetailsDTO object.
     */
    public EmployerAllDetailsDTO toEmployerAllDetailsDTO(Employer employer) {
        EmployerDTO employerDTO = modelMapper.map(employer, EmployerDTO.class);
        EmployerBankDetailsDTO bankDetailsDTO = null;
        if (employer.getEmployerBankDetails() != null) {
            bankDetailsDTO = modelMapper.map(employer.getEmployerBankDetails(), EmployerBankDetailsDTO.class);
        }
        return new EmployerAllDetailsDTO(employerDTO, bankDetailsDTO);
    }


    /**
     * This method is used to convert an EmployerBankDetailsDTO object to an EmployerBankDetails object.
     * It first checks if the EmployerBankDetailsDTO object is null.
     * If it is not null, it creates a new EmployerBankDetails object and sets its properties using
     * the EmployerBankDetailsDTO object.
     * Finally, it returns the created EmployerBankDetails object.
     *
     * @param bankDetailsDTO The EmployerBankDetailsDTO object to be converted.
     * @return The converted EmployerBankDetails object, or null if the EmployerBankDetailsDTO object was null.
     */
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