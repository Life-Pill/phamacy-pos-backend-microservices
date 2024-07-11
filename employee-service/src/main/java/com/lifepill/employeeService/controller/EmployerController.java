package com.lifepill.employeeService.controller;

import com.lifepill.employeeService.dto.APIResponseDTO.EmployeeBranchApiResponseDTO;
import com.lifepill.employeeService.dto.EmployerBankDetailsDTO;
import com.lifepill.employeeService.dto.EmployerDTO;
import com.lifepill.employeeService.dto.EmployerWithBankDTO;
import com.lifepill.employeeService.dto.EmployerWithoutImageDTO;
import com.lifepill.employeeService.dto.requestDTO.EmployerAllDetailsUpdateDTO;
import com.lifepill.employeeService.dto.requestDTO.EmployerUpdateAccountDetailsDTO;
import com.lifepill.employeeService.dto.requestDTO.EmployerUpdateBankAccountDTO;
import com.lifepill.employeeService.dto.responseDTO.EmployerAllDetailsDTO;
import com.lifepill.employeeService.exception.NotFoundException;
import com.lifepill.employeeService.service.EmployerService;
import com.lifepill.employeeService.util.StandardResponse;
import com.lifepill.employeeService.util.mappers.EmployerMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Controller class for managing employer-related operations.
 */
@RestController
@RequestMapping("lifepill/v1/employers")
@AllArgsConstructor
public class EmployerController {

    private EmployerService employerService;
    private EmployerMapper employerMapper;

    /**
     * Saves an employer without an image.
     *
     * @param cashierWithoutImageDTO DTO containing details of the employer without image.
     * @return A string indicating the success of the operation.
     */
    @PostMapping("/save-without-image")
    public ResponseEntity<StandardResponse> saveCashierWithoutImage(@RequestBody EmployerWithoutImageDTO cashierWithoutImageDTO) {
        employerService.saveEmployerWithoutImage(cashierWithoutImageDTO);

        return new ResponseEntity<>(
                new StandardResponse(201, "successfully saved", cashierWithoutImageDTO),
                HttpStatus.CREATED
        );
    } /**
     * Saves an employer with an image.
     *
     * @param employerDTO DTO containing details of the employer including image.
     * @param file        MultipartFile representing the image file.
     * @return A string indicating the success of the operation.
     * @throws IOException If an I/O error occurs.
     */
    @PostMapping("/save-with-image")
    public ResponseEntity<StandardResponse> saveEmployerWithImage(
            @ModelAttribute EmployerDTO employerDTO,
            @RequestParam("file") MultipartFile file
    )
            throws IOException {
        // Check if a file is provided
        if (!file.isEmpty()) {
            // Convert MultipartFile to byte array
            byte[] profileImage = file.getBytes();
            // Set the profile image in the employerDTO
            employerDTO.setProfileImage(profileImage);
        }
        // Save the cashier along with the profile photo
        employerService.saveEmployer(employerDTO);
        return new ResponseEntity<>(
                new StandardResponse(201, "successfully saved", employerDTO),
                HttpStatus.CREATED
        );
    }

    /**
     * Retrieves the profile photo of an employer by ID.
     *
     * @param employerId The ID of the employer.
     * @return ResponseEntity containing the profile photo byte array.
     */
    @GetMapping("/profile-photo/{employerId}")
    @Transactional
    public ResponseEntity<byte[]> getProfilePhoto(@PathVariable long employerId) {
        // Retrieve the cashier entity by ID
        EmployerDTO cashier = employerService.getEmployerById(employerId);

        // Check if the cashier exists
        if (cashier != null && cashier.getProfileImage() != null) {
            // Return the profile image byte array with appropriate headers
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG) // Adjust content type based on image format
                    .body(cashier.getProfileImage());
        } else {
            // If the cashier or profile photo doesn't exist, return a not found response
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates details of an employer.
     *
     * @param employerId                 The ID of the employer to be updated.
     * @param cashierAllDetailsUpdateDTO DTO containing updated details of the employer.
     * @return A string indicating the success of the operation.
     */
    @PutMapping("/update/{employerId}")
    @Transactional
    public ResponseEntity<StandardResponse> updateEmployer(
            @PathVariable Long employerId,
            @RequestBody EmployerAllDetailsUpdateDTO cashierAllDetailsUpdateDTO
    ) {
        String message = employerService.updateEmployer(employerId, cashierAllDetailsUpdateDTO);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(
                        201,
                        message,
                        cashierAllDetailsUpdateDTO
                ),
                HttpStatus.OK
        );
    }

    /**
     * Updates the account details of an employer.
     *
     * @param cashierUpdateAccountDetailsDTO DTO containing updated account details of the employer.
     * @return A string indicating the success of the operation.
     */
    @PutMapping("/updateAccountDetails")
    @Transactional
    public ResponseEntity<StandardResponse> updateEmployerAccountDetails(
            @RequestBody EmployerUpdateAccountDetailsDTO cashierUpdateAccountDetailsDTO
    ) {
        String message = employerService.updateEmployerAccountDetails(cashierUpdateAccountDetailsDTO);
        return new ResponseEntity<>(
                new StandardResponse(
                        201,
                        message,
                        cashierUpdateAccountDetailsDTO
                ),
                HttpStatus.OK
        );
    }

    /**
     * Updates the bank account details of an employer.
     *
     * @param employerId                   The ID of the employer to update.
     * @param employerUpdateBankAccountDTO The DTO containing the updated bank account details.
     * @return ResponseEntity containing the updated employer data
     * along with bank account details,
     * or an HTTP status indicating the failure if the employer is not found.
     */
    @PutMapping("/updateEmployerBankAccountDetailsWithId/{employerId}")
    @Transactional
    public ResponseEntity<StandardResponse> updateEmployerBankAccountDetailsWithId(
            @PathVariable long employerId,
            @RequestBody EmployerUpdateBankAccountDTO employerUpdateBankAccountDTO
    ) {
        try {
            EmployerWithBankDTO employerWithBankDTO = employerService
                    .updateEmployerBankAccountDetails(employerUpdateBankAccountDTO);
            EmployerBankDetailsDTO bankDetailsDTO = employerService
                    .getEmployerBankDetailsById(employerId);
            employerWithBankDTO.setEmployerBankDetails(
                    employerMapper.mapBankDetailsDTOToEntity(bankDetailsDTO)
            ); // Utilize the mapper// Map DTO to Entity
            return ResponseEntity.ok(
                    new StandardResponse(
                            201,
                            "SUCCESS",
                            employerWithBankDTO
                    )
            );
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            new StandardResponse(404, ex.getMessage(), null)
                    );
        }
    }

    /**
     * Retrieves an employer by ID.
     *
     * @param employerId The ID of the employer to retrieve.
     * @return The EmployerDTO object representing the employer.
     */
    @GetMapping(path = "/get-by-employee-id", params = "employerId")
    @Transactional
    public ResponseEntity<StandardResponse> getEmployerById(@RequestParam(value = "employerId") int employerId) {
        EmployerDTO employerDTO = employerService.getEmployerById(employerId);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(200, "SUCCESS", employerDTO),
                HttpStatus.OK
        );
    }

    /**
     * Retrieves all details of an employer by ID.
     *
     * @param employerId The ID of the employer to retrieve.
     * @return The EmployerAllDetailsDTO object representing the employer.
     */
    @GetMapping(path = "/get-all-employee-details-by-id", params = "employerId")
    @Transactional
    public ResponseEntity<StandardResponse> getEmployerByIdWithImage(
            @RequestParam(value = "employerId") int employerId
    ) {
        EmployerAllDetailsDTO employerAllDetailsDTO = employerService.getAllDetails(employerId);

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(200, "SUCCESS", employerAllDetailsDTO),
                HttpStatus.OK
        );
    }

    /**
     * Retrieves the profile photo of an employer by ID.
     *
     * @param employerId The ID of the employer.
     * @return ResponseEntity containing the profile photo byte array.
     */
    @GetMapping("/view-image/{employerId}")
    @Transactional
    public ResponseEntity<byte[]> viewImage(@PathVariable long employerId) {
        byte[] imageData = employerService.getImageData(employerId);

        if (imageData != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Adjust the media type based on your image format
            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes an employer by ID.
     *
     * @param employerId The ID of the employer to delete.
     * @return A string indicating the success of the operation.
     */
    @DeleteMapping(path = "/delete-employer-by-Id/{employerId}")
    public ResponseEntity<StandardResponse> deleteEmployer(@PathVariable(value = "employerId") long employerId) {
        String deleted = employerService.deleteEmployer(employerId);
        return new ResponseEntity<>(
                new StandardResponse(201, deleted, null),
                HttpStatus.OK
        );
    }

    /**
     * Retrieves all employers.
     *
     * @return ResponseEntity containing a list of all employers.
     */
    @GetMapping(path = "/get-all-employers")
    public ResponseEntity<StandardResponse> getAllEmployers() {
        List<EmployerAllDetailsDTO> allEmployer = employerService.getAllEmployer();
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(201, "SUCCESS", allEmployer),
                HttpStatus.OK
        );
    }

    /**
     * Retrieves all employers.
     *
     * @return ResponseEntity containing a list of all employers.
     */
    @GetMapping(path = "/get-all-employers-by-active-state/{status}")
    @Transactional
    public List<EmployerDTO> getAllEmployerByActiveState(@PathVariable(value = "status") boolean activeState) {
        List<EmployerDTO> allemployer = employerService.getAllEmployerByActiveState(activeState);
        return allemployer;
    }

    /**
     * Retrieves bank details of all employers.
     *
     * @return ResponseEntity containing a StandardResponse object with a list of employer bank details.
     */
    @GetMapping(path = "/get-all-employers-bank-details")
    @Transactional
    public ResponseEntity<StandardResponse> getAllEmployerBankDetails() {
        List<EmployerUpdateBankAccountDTO> allCashiersBankDetails = employerService.getAllEmployerBankDetails();
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(201, "SUCCESS", allCashiersBankDetails),
                HttpStatus.OK
        );
    }

    /**
     * Retrieves bank details of an employer by ID.
     *
     * @param employerId The ID of the employer.
     * @return ResponseEntity containing the bank details of the employer,
     * or an HTTP status indicating the failure if the employer is not found.
     */
    @GetMapping("/bank-details/{employerId}")
    public ResponseEntity<StandardResponse> getEmployerBankDetailsById(@PathVariable long employerId) {
        try {
            // Retrieve bank details of the employer by ID
            EmployerBankDetailsDTO bankDetailsDTO = employerService.getEmployerBankDetailsById(employerId);

            if (bankDetailsDTO != null) {
                // Return the bank details with a success response
                return ResponseEntity.ok(
                        new StandardResponse(200, "SUCCESS", bankDetailsDTO)
                );
            } else {
                // If the bank details are not found, return a not found response
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new StandardResponse(
                                404,
                                "Bank details not found for employer ID: " + employerId,
                                null)
                        );
            }
        } catch (NotFoundException ex) {
            // If an exception occurs (e.g., employer not found), return a not found response
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new StandardResponse(404, ex.getMessage(), null));
        }
    }

    /**
     * Retrieves all employers associated with a specific branch by the employer's ID.
     *
     * @param employerId The ID of the employer whose branch details are to be retrieved.
     * @return ResponseEntity containing a StandardResponse object with a list of all employers associated with the branch.
     */
    @GetMapping("/get-all-employers-with-branch-details/{employerId}")
    public ResponseEntity<StandardResponse> getAllEmployerByBranchId(@PathVariable long employerId) {
        List<EmployeeBranchApiResponseDTO> allEmployer = employerService.getAllDetailsOfEmployerByEmployeeID(employerId);
        return new ResponseEntity<>(
                new StandardResponse(201, "SUCCESS", allEmployer),
                HttpStatus.OK
        );
    }

    /**
     * Checks if an employer exists by their ID.
     *
     * @param employerId The ID of the employer to check.
     * @return ResponseEntity containing a StandardResponse object with a message indicating whether the employer exists.
     */
    @GetMapping("/check-employer-exists-by-id/{employerId}")
    public ResponseEntity<StandardResponse> checkEmployerExistsById(@PathVariable long employerId) {
        boolean exists = employerService.checkEmployerExistsById(employerId);
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        exists ? "Employer exists" : "Employer does not exist",
                        exists
                ),
                HttpStatus.OK
        );
    }


}