package com.lifepill.employeeService.service;

import com.lifepill.employeeService.dto.APIResponseDTO.EmployeeBranchApiResponseDTO;
import com.lifepill.employeeService.dto.EmployerBankDetailsDTO;
import com.lifepill.employeeService.dto.EmployerDTO;
import com.lifepill.employeeService.dto.EmployerWithBankDTO;
import com.lifepill.employeeService.dto.EmployerWithoutImageDTO;
import com.lifepill.employeeService.dto.requestDTO.EmployerAllDetailsUpdateDTO;
import com.lifepill.employeeService.dto.requestDTO.EmployerUpdateAccountDetailsDTO;
import com.lifepill.employeeService.dto.requestDTO.EmployerUpdateBankAccountDTO;
import com.lifepill.employeeService.dto.responseDTO.EmployerAllDetailsDTO;

import java.util.List;

public interface EmployerService {

    void saveEmployerWithoutImage(EmployerWithoutImageDTO cashierWithoutImageDTO);

    void saveEmployer(EmployerDTO employerDTO);

    EmployerDTO getEmployerById(long employerId);

    String updateEmployer(Long employerId, EmployerAllDetailsUpdateDTO cashierAllDetailsUpdateDTO);

    String updateEmployerAccountDetails(EmployerUpdateAccountDetailsDTO cashierUpdateAccountDetailsDTO);

    EmployerWithBankDTO updateEmployerBankAccountDetails(EmployerUpdateBankAccountDTO employerUpdateBankAccountDTO);

    EmployerBankDetailsDTO getEmployerBankDetailsById(long employerId);

    EmployerAllDetailsDTO getAllDetails(int employerId);

    byte[] getImageData(long employerId);

    String deleteEmployer(long employerId);

    List<EmployerAllDetailsDTO> getAllEmployer();

    List<EmployerDTO> getAllEmployerByActiveState(boolean activeState);

    List<EmployerUpdateBankAccountDTO> getAllEmployerBankDetails();

    List<EmployerAllDetailsDTO> getAllEmployerByBranchId(int branchId);

    List<EmployeeBranchApiResponseDTO> getAllDetailsOfEmployerByEmployeeID(long employerId);

    boolean checkEmployerExistsById(long employerId);

    EmployerAllDetailsDTO getManagerByBranchId(int branchId);
}
