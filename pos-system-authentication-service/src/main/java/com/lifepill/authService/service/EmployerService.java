package com.lifepill.authService.service;


import com.lifepill.authService.dto.EmployerBankDetailsDTO;
import com.lifepill.authService.dto.EmployerWithBankDTO;
import com.lifepill.authService.dto.EmployerDTO;
import com.lifepill.authService.dto.EmployerWithoutImageDTO;
import com.lifepill.authService.dto.requestDTO.EmployerUpdate.*;
import com.lifepill.authService.entity.enums.Role;

import java.util.List;

public interface EmployerService {

    String saveEmployer(EmployerDTO employerDTO);

    String saveEmployerWithoutImage(EmployerWithoutImageDTO employerWithoutImageDTO);


    String updateEmployer(Long cashierId, EmployerAllDetailsUpdateDTO employerAllDetailsUpdateDTO);

    EmployerDTO getEmployerById(long cashierId);

    String deleteEmployer(long cashierId);

    List<EmployerDTO> getAllEmployer();

    List<EmployerDTO> getAllEmployerByActiveState(boolean activeState);

    String updateEmployerAccountDetails(EmployerUpdateAccountDetailsDTO employerUpdateAccountDetailsDTO);

    String updateEmployerPassword(EmployerPasswordResetDTO employerPasswordResetDTO);

    String updateRecentPin(EmployerRecentPinUpdateDTO employerRecentPinUpdateDTO);

    EmployerWithBankDTO updateEmployerBankAccountDetails(EmployerUpdateBankAccountDTO employerUpdateBankAccountDTO);

    List<EmployerUpdateBankAccountDTO> getAllEmployerBankDetails();

    EmployerDTO getEmployerByIdWithImage(long employerId);

    byte[] getImageData(long cashierId);

    String updateEmployerBankAccountDetailsByCashierId(long employerId, EmployerUpdateBankAccountDTO employerUpdateBankAccountDTO);

    List<EmployerDTO> getAllEmployerByBranchId(long branchId);

    List<EmployerDTO> getAllEmployerByRole(Role role);

    EmployerDTO getEmployerByUsername(String username);

    EmployerBankDetailsDTO getEmployerBankDetailsById(long employerId);

    List<EmployerWithBankDTO> getAllEmployersWithBankDetails();

    EmployerWithBankDTO getEmployerWithBankDetailsById(long employerId);
}
