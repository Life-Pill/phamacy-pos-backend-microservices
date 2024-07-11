package com.lifepill.authService.dto.requestDTO.EmployerUpdate;

import com.lifepill.authService.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * The type Employer update account details dto.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployerUpdateAccountDetailsDTO {
    private long employerId;
    private String employerFirstName;
    private String employerLastName;
    private Gender gender;
    private String employerAddress;
    private Date dateOfBirth;


}
