package com.lifepill.employeeService.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Employer recent pin update dto.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployerRecentPinUpdateDTO {
    private long employerId;
    private int pin;
}
