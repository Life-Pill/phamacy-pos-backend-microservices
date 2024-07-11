package com.lifepill.employeeService.service.impl;

import com.lifepill.employeeService.dto.*;
import com.lifepill.employeeService.dto.APIResponseDTO.EmployeeBranchApiResponseDTO;
import com.lifepill.employeeService.dto.requestDTO.EmployerAllDetailsUpdateDTO;
import com.lifepill.employeeService.dto.requestDTO.EmployerUpdateAccountDetailsDTO;
import com.lifepill.employeeService.dto.requestDTO.EmployerUpdateBankAccountDTO;
import com.lifepill.employeeService.dto.responseDTO.EmployerAllDetailsDTO;
import com.lifepill.employeeService.entity.Employer;
import com.lifepill.employeeService.entity.EmployerBankDetails;
import com.lifepill.employeeService.entity.enums.Role;
import com.lifepill.employeeService.exception.EntityDuplicationException;
import com.lifepill.employeeService.exception.EntityNotFoundException;
import com.lifepill.employeeService.exception.NotFoundException;
import com.lifepill.employeeService.repository.EmployerBankDetailsRepository;
import com.lifepill.employeeService.repository.EmployerRepository;
import com.lifepill.employeeService.service.APIClient;
import com.lifepill.employeeService.service.EmployerService;
import com.lifepill.employeeService.util.StandardResponse;
import com.lifepill.employeeService.util.mappers.EmployerMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * EmployerServiceIMPL is a service class that implements the EmployerService interface.
 * It provides the functionality to manage employers in the system.
 */
@Service
@Transactional
@AllArgsConstructor
public class EmployerServiceIMPL implements EmployerService {

    private EmployerRepository employerRepository;
    private EmployerBankDetailsRepository employerBankDetailsRepository;
    private ModelMapper modelMapper;
    private final EmployerMapper employerMapper;
    private APIClient apiClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployerServiceIMPL.class);

    /**
     * This method is used to save an employer without an image.
     * It first checks if the employer already exists in the system by their id or email.
     * If the employer already exists, it throws an EntityDuplicationException.
     * If the employer does not exist, it retrieves the branch associated with the employer by its id.
     * If the branch does not exist, it throws a NotFoundException.
     * If the branch exists, it maps the EmployerWithoutImageDTO to an Employer entity and saves it in the database.
     * If the employer is not found after saving, it throws a NotFoundException.
     * If the employer is saved successfully, it returns a success message.
     *
     * @param employerWithoutImageDTO The employer data transfer object without an image.
     * @throws EntityDuplicationException If the employer already exists.
     * @throws NotFoundException          If the associated branch is not found.
     */
    @Override
    public void saveEmployerWithoutImage(EmployerWithoutImageDTO employerWithoutImageDTO) {
        // check if the employer already exists email or id
        if (employerRepository.existsById(employerWithoutImageDTO.getEmployerId())
                || employerRepository.existsAllByEmployerEmail(employerWithoutImageDTO.getEmployerEmail())) {
            throw new EntityDuplicationException("Employer already exists");
        } else {

            ResponseEntity<StandardResponse> standardResponseResponseEntity =
                    apiClient.getBranchById(employerWithoutImageDTO.getBranchId());

            if (standardResponseResponseEntity.getStatusCode() != HttpStatus.OK) {
                String errorMessage = Objects.requireNonNull(standardResponseResponseEntity.getBody()).getMessage();
                throw new NotFoundException(errorMessage);
            }

            BranchDTO branchDTO = modelMapper.map(
                    Objects.requireNonNull(standardResponseResponseEntity.getBody())
                            .getData(), BranchDTO.class
            );

            //check if the branch is existing
            assert branchDTO != null;
            if (branchDTO.getBranchId() == 0) {
                throw new NotFoundException("Branch not found for the given branch id:"
                        + employerWithoutImageDTO.getBranchId());
            }

            Employer employer = modelMapper.map(employerWithoutImageDTO, Employer.class);

            String savedEmployer = String.valueOf(employerRepository.findByEmployerEmail(employerWithoutImageDTO.getEmployerEmail()));
            if (savedEmployer != null) {
                employerRepository.save(employer);
                long employerId = employer.getEmployerId();
                employerWithoutImageDTO.setEmployerId(employerId);

            } else {
                throw new NotFoundException("Employer not found after saving");
            }
        }
    }

    /**
     * Saves an employer with image.
     *
     * @param employerDTO The employer data transfer object.
     * @throws EntityDuplicationException If the employer already exists.
     * @throws NotFoundException          If the associated branch is not found.
     */
    @Override
    public void saveEmployer(EmployerDTO employerDTO) {
        // check if the cashier already exists email or id
        if (employerRepository.existsById(employerDTO.getEmployerId()) ||
                employerRepository.existsAllByEmployerEmail(employerDTO.getEmployerEmail())) {
            throw new EntityDuplicationException("Employer already exists");
        } else {
            // Retrieve the Branch entity by its ID
            ResponseEntity<StandardResponse> standardResponseResponseEntity =
                    apiClient.getBranchById(employerDTO.getBranchId());

            // Check if the branch exists
            if (standardResponseResponseEntity.getStatusCode() != HttpStatus.OK) {
                String errorMessage = Objects.requireNonNull(standardResponseResponseEntity.getBody()).getMessage();
                throw new NotFoundException(errorMessage);
            }
            //TODO: Check if the branch exists
            BranchDTO branchDTO = modelMapper.map(
                    Objects.requireNonNull(standardResponseResponseEntity.getBody())
                            .getData(), BranchDTO.class
            );
            if (branchDTO.getBranchId() == 0) {
                throw new NotFoundException("Branch not found for the given branch id:"
                        + employerDTO.getBranchId());
            }
            // TODO: response employeeID get as 0. need to correct

            // Map EmployerDTO to Employer entity
            Employer employer = modelMapper.map(employerDTO, Employer.class);
            // Save the Employer entity
            employerRepository.save(employer);
        }
    }

    /**
     * Retrieves an employer by their ID.
     *
     * @param employerId The ID of the employer to retrieve.
     * @return The DTO representing the employer.
     * @throws NotFoundException If no employer is found with the specified ID.
     */
    @Override
    public EmployerDTO getEmployerById(long employerId) {
        if (employerRepository.existsById(employerId)) {
            Employer employer = employerRepository.getReferenceById(employerId);
            return modelMapper.map(employer, EmployerDTO.class);
        } else {
            throw new NotFoundException("No employer found for that id");
        }
    }

    /**
     * Updates details of an employer.
     *
     * @param employerId                  The ID of the employer to update.
     * @param employerAllDetailsUpdateDTO The DTO containing updated employer details.
     * @return A message indicating the success of the operation.
     * @throws NotFoundException If the employer with the given ID is not found.
     */
    @Override
    public String updateEmployer(Long employerId, EmployerAllDetailsUpdateDTO employerAllDetailsUpdateDTO) {
        // Check if the employer exists
        Employer existingEmployer = employerRepository.findById(employerId)
                .orElseThrow(() -> new NotFoundException("Employer not found with ID: " + employerId));

        // Check if the email is already associated with another employer
        if (!existingEmployer.getEmployerEmail().equals(
                employerAllDetailsUpdateDTO.getEmployerEmail()) &&
                employerRepository.existsAllByEmployerEmail(employerAllDetailsUpdateDTO.getEmployerEmail()
                )
        ) {
            throw new EntityDuplicationException("Email already exists");
        }

        // Map updated details to existing employer
        modelMapper.map(employerAllDetailsUpdateDTO, existingEmployer);

        // If the password is provided, encode it before updating
        //TODO: password encoder
     /*   if (employerAllDetailsUpdateDTO.getEmployerPassword() != null) {

            String encodedPassword = passwordEncoder.encode(employerAllDetailsUpdateDTO.getEmployerPassword());
            existingEmployer.setEmployerPassword(encodedPassword);
        }*/

        //check if the provide branch id is existing or not
        ResponseEntity<StandardResponse> standardResponseResponseEntity =
                apiClient.getBranchById(employerAllDetailsUpdateDTO.getBranchId());

        if (standardResponseResponseEntity.getStatusCode() != HttpStatus.OK) {
            String errorMessage = Objects.requireNonNull(standardResponseResponseEntity.getBody()).getMessage();
            throw new NotFoundException(errorMessage);

        }
        // Save the updated employer
        employerRepository.save(existingEmployer);

        return "Employer: " + employerId + ", updated successfully";
    }

    /**
     * Updates account details of an employer.
     *
     * @param employerUpdateAccountDetailsDTO The DTO containing updated employer account details.
     * @return A message indicating the success of the operation.
     * @throws NotFoundException If the employer with the given ID is not found.
     */
    @Override
    public String updateEmployerAccountDetails(EmployerUpdateAccountDetailsDTO employerUpdateAccountDetailsDTO) {
        if (employerRepository.existsById(employerUpdateAccountDetailsDTO.getEmployerId())) {
            Employer employer = employerRepository.getReferenceById(employerUpdateAccountDetailsDTO.getEmployerId());

            modelMapper.map(employerUpdateAccountDetailsDTO, employer);

            employerRepository.save(employer);

            return "Successfully Update employer account details";
        } else {
            throw new NotFoundException("No data found for that id");
        }
    }


    /**
     * Updates the bank account details of an employer.
     *
     * @param employerUpdateBankAccountDTO The DTO containing the updated bank account details.
     * @return A DTO containing the updated employer data along with bank account details.
     * @throws NotFoundException If the employer with the given ID is not found.
     */
    @Override
    public EmployerWithBankDTO updateEmployerBankAccountDetails(EmployerUpdateBankAccountDTO employerUpdateBankAccountDTO) {
        long employerId = employerUpdateBankAccountDTO.getEmployerId();

        // Check if the employer exists
        if (employerRepository.existsById(employerId)) {
            Employer employer = employerRepository.getReferenceById(employerId);
            // Check if the employer already has bank details

            Optional<EmployerBankDetails> existingBankDetailsOpt =
                    employerBankDetailsRepository.findById(
                            employerUpdateBankAccountDTO.getEmployerBankDetailsId()
                    );

            EmployerBankDetails bankDetails = getEmployerBankDetails(
                    employerUpdateBankAccountDTO, existingBankDetailsOpt, employerId
            );

            // Save the bank details
            employerBankDetailsRepository.save(bankDetails);

            // Associate the bank details with the employer
            employer.setEmployerBankDetails(bankDetails);
            employerRepository.save(employer);

            // Prepare the DTO to return
            EmployerWithBankDTO employerWithBankDTO = modelMapper.map(
                    employer, EmployerWithBankDTO.class
            );

            // set Branch ID
            employerWithBankDTO.setBranchId(employer.getBranchId());

            return employerWithBankDTO;
        } else {
            throw new NotFoundException("No data found for that employer ID");
        }
    }

    private static EmployerBankDetails getEmployerBankDetails(
            EmployerUpdateBankAccountDTO employerUpdateBankAccountDTO,
            Optional<EmployerBankDetails> existingBankDetailsOpt, long employerId
    ) {
        EmployerBankDetails bankDetails;

        if (existingBankDetailsOpt.isPresent()) {
            // Update existing bank details
            bankDetails = existingBankDetailsOpt.get();
        } else {
            // Create new bank details
            bankDetails = new EmployerBankDetails();
            bankDetails.setEmployerId(employerId); // Set the employer ID for the new bank details
        }
        // Update the bank details
        bankDetails.setBankName(employerUpdateBankAccountDTO.getBankName());
        bankDetails.setBankBranchName(employerUpdateBankAccountDTO.getBankBranchName());
        bankDetails.setBankAccountNumber(employerUpdateBankAccountDTO.getBankAccountNumber());
        bankDetails.setEmployerDescription(employerUpdateBankAccountDTO.getEmployerDescription());
        bankDetails.setMonthlyPayment(employerUpdateBankAccountDTO.getMonthlyPayment());
        bankDetails.setMonthlyPaymentStatus(employerUpdateBankAccountDTO.isMonthlyPaymentStatus());
        return bankDetails;
    }

    /**
     * Retrieves the bank details of an employer by their ID.
     *
     * @param employerId The ID of the employer whose bank details are to be retrieved.
     * @return EmployerBankDetailsDTO containing the bank details of the specified employer.
     * @throws EntityNotFoundException if no employer is found with the given ID.
     */
    @Override
    public EmployerBankDetailsDTO getEmployerBankDetailsById(long employerId) {
        Employer employer = employerRepository.findById(employerId)
                .orElseThrow(() -> new EntityNotFoundException("Employer not found with id: " + employerId));

        EmployerBankDetails bankDetails = employer.getEmployerBankDetails();

        return modelMapper.map(bankDetails, EmployerBankDetailsDTO.class);
    }

    /**
     * This method is used to retrieve all details of an employer by their ID.
     * It first retrieves the basic employer details by calling the getEmployerById method with the provided employer ID.
     * Then, it retrieves the bank details of the employer by calling the getEmployerBankDetailsById method with the same employer ID.
     * It then creates a new EmployerAllDetailsDTO object and sets the retrieved employer and bank details into it.
     * Finally, it returns the EmployerAllDetailsDTO object which now contains all details of the employer.
     *
     * @param employerId The ID of the employer whose details are to be retrieved.
     * @return EmployerAllDetailsDTO containing all details of the specified employer.
     */
    @Override
    public EmployerAllDetailsDTO getAllDetails(int employerId) {
        EmployerDTO employerDTO = getEmployerById(employerId);
        EmployerBankDetailsDTO employerBankDetailsDTO = getEmployerBankDetailsById(employerId);

        EmployerAllDetailsDTO employerAllDetailsDTO = new EmployerAllDetailsDTO();
        employerAllDetailsDTO.setEmployerDTO(employerDTO);
        employerAllDetailsDTO.setEmployerBankDetailsDTO(employerBankDetailsDTO);

        return employerAllDetailsDTO;
    }

    @Override
    public byte[] getImageData(long employerId) {
        Optional<Employer> branchOptional = employerRepository.findById( employerId);
        return branchOptional.map(Employer::getProfileImage).orElse(null);
    }

    /**
     * Deletes an employer with the specified ID.
     *
     * @param employerId The ID of the employer to be deleted.
     * @return A message indicating the successful deletion of the employer.
     * @throws NotFoundException If no employer is found with the specified ID.
     */
    @Override
    public String deleteEmployer(long employerId) {
        if (employerRepository.existsById(employerId)){
            employerRepository.deleteById(employerId);

            return "deleted successfully : "+ employerId;
        }else {
            throw new NotFoundException("No employer found for that id");
        }
    }

    /**
     * Retrieves all employers in the system.
     * It first retrieves all Employer entities from the repository.
     * Then, it converts each Employer entity to an EmployerAllDetailsDTO object by calling the convertToEmployerAllDetailsDTO method.
     * Finally, it returns a list of EmployerAllDetailsDTO objects.
     *
     * @return List of EmployerAllDetailsDTO containing all details of all employers.
     */
    @Override
    public List<EmployerAllDetailsDTO> getAllEmployer() {
        List<Employer> employers = employerRepository.findAll();
        return employers.stream()
                .map(this::convertToEmployerAllDetailsDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converts an Employer entity to an EmployerAllDetailsDTO object.
     * It first maps the Employer entity to an EmployerDTO object.
     * Then, it checks if the Employer entity has associated bank details.
     * If it does, it maps the EmployerBankDetails entity to an EmployerBankDetailsDTO object.
     * Finally, it creates a new EmployerAllDetailsDTO object and sets the EmployerDTO and EmployerBankDetailsDTO objects into it.
     *
     * @param employer The Employer entity to be converted.
     * @return EmployerAllDetailsDTO containing all details of the specified employer.
     */
    private EmployerAllDetailsDTO convertToEmployerAllDetailsDTO(Employer employer) {
        EmployerDTO employerDTO = modelMapper.map(employer, EmployerDTO.class);
        EmployerBankDetailsDTO employerBankDetailsDTO = null;
        if (employer.getEmployerBankDetails() != null) {
            employerBankDetailsDTO = modelMapper.map(employer.getEmployerBankDetails(), EmployerBankDetailsDTO.class);
        }
        return new EmployerAllDetailsDTO(employerDTO, employerBankDetailsDTO);
    }


    /**
     * Retrieves all employers with the specified active state.
     *
     * @param activeState The active state of the employers to retrieve.
     * @return A list of DTOs containing employer details.
     * @throws NotFoundException If no employers are found with the specified active state.
     */
    @Override
    public List<EmployerDTO> getAllEmployerByActiveState(boolean activeState) {
        List<Employer> getAllEmployers = employerRepository.findByIsActiveStatusEquals(activeState);
        return getEmployerDTOS(getAllEmployers);
    }

    private List<EmployerDTO> getEmployerDTOS(List<Employer> getAllEmployers) {
        if (!getAllEmployers.isEmpty()){
            List<EmployerDTO> employerDTOList = new ArrayList<>();
            for (Employer employer : getAllEmployers){
                EmployerDTO employerDTO = modelMapper.map(employer, EmployerDTO.class);
                employerDTOList.add(employerDTO);
            }
            return employerDTOList;
        }else {
            throw new NotFoundException("No Employer Found");
        }
    }


    /**
     * Retrieves all employer bank account details.
     *
     * @return A list of DTOs containing employer bank account details.
     * @throws NotFoundException If no employer bank details are found.
     */
    @Override
    public List<EmployerUpdateBankAccountDTO> getAllEmployerBankDetails() {
        List<EmployerBankDetails> getAllCashiersBankDetails = employerBankDetailsRepository.findAll();

        if (!getAllCashiersBankDetails.isEmpty()){
            List<EmployerUpdateBankAccountDTO> cashierUpdateBankAccountDTOList = new ArrayList<>();
            for (EmployerBankDetails employerBankDetails : getAllCashiersBankDetails){
                EmployerUpdateBankAccountDTO cashierUpdateBankAccountDTO =
                        modelMapper.map(employerBankDetails, EmployerUpdateBankAccountDTO.class);
                cashierUpdateBankAccountDTOList.add(cashierUpdateBankAccountDTO);
            }
            return cashierUpdateBankAccountDTOList;
        }else {
            throw new NotFoundException("No Employer Bank Details Found");
        }
    }

    /**
     * This method is used to retrieve all employers associated with a specific branch.
     * It first retrieves all Employer entities associated with the given branch ID from the repository.
     * Then, it converts each Employer entity to an EmployerAllDetailsDTO object using the employerMapper.
     * Finally, it returns a list of EmployerAllDetailsDTO objects.
     *
     * @param branchId The ID of the branch whose employers are to be retrieved.
     * @return List of EmployerAllDetailsDTO containing all details of all employers associated with the specified branch.
     */
    @Override
    public List<EmployerAllDetailsDTO> getAllEmployerByBranchId(int branchId) {
        List<Employer> employers = employerRepository.findByBranchId(branchId);
        return employers.stream()
                .map(employerMapper::toEmployerAllDetailsDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all details of an employer by their ID using a circuit breaker pattern.
     * The circuit breaker pattern is used to prevent system failure and maintain system stability when calling external services.
     * If the call to the external service fails, the circuit breaker goes into the open state and the fallback method is called.
     *
     * @param employerId The ID of the employer whose details are to be retrieved.
     * @return A list of EmployeeBranchApiResponseDTO containing all details of the employer.
     * @throws EntityNotFoundException If no employer is found with the given ID.
     */
    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getAllDetailsOfEmployerByEmployeeIDFallback")
    @Override
    public List<EmployeeBranchApiResponseDTO> getAllDetailsOfEmployerByEmployeeID(long employerId) {

        LOGGER.info("Inside getAllDetailsOfEmployerByEmployeeID method");
        List<EmployeeBranchApiResponseDTO> employeeBranchApiResponseDTOList = new ArrayList<>();
        EmployeeBranchApiResponseDTO employeeBranchApiResponseDTO = new EmployeeBranchApiResponseDTO();
        //get employer by id
        Employer employer = employerRepository.findById(employerId)
                .orElseThrow(() -> new EntityNotFoundException("Employer not found with id: " + employerId));
        //get employerDTO
        EmployerDTO employerDTO = modelMapper.map(employer, EmployerDTO.class);
        //get branchDTO
        ResponseEntity<StandardResponse> standardResponseResponseEntity =
                apiClient.getBranchById(employer.getBranchId());
        BranchDTO branchDTO = modelMapper.map(
                Objects.requireNonNull(standardResponseResponseEntity.getBody())
                        .getData(), BranchDTO.class
        );
        employeeBranchApiResponseDTO.setEmployerDTO(employerDTO);
        employeeBranchApiResponseDTO.setBranchDTO(branchDTO);
        employeeBranchApiResponseDTOList.add(employeeBranchApiResponseDTO);
        return employeeBranchApiResponseDTOList;
    }

    /**
     * Checks if an employer exists by their ID.
     *
     * @param employerId The ID of the employer to check.
     * @return true if the employer exists, false otherwise.
     */
    @Override
    public boolean checkEmployerExistsById(long employerId) {
        return employerRepository.existsById(employerId);
    }

    /**
     * Fallback method for getAllDetailsOfEmployerByEmployeeID.
     * This method is called when the circuit breaker is open and the call to the external service fails.
     * It returns a list of EmployeeBranchApiResponseDTO with null values.
     *
     * @param employerId The ID of the employer whose details were to be retrieved.
     * @param throwable The exception that caused the circuit breaker to open.
     * @return A list of EmployeeBranchApiResponseDTO with null values.
     */
    public List<EmployeeBranchApiResponseDTO> getAllDetailsOfEmployerByEmployeeIDFallback(long employerId, Throwable throwable) {
        LOGGER.error("Inside getAllDetailsOfEmployerByEmployeeIDFallback method");
        //TODO: need to add default values
        List<EmployeeBranchApiResponseDTO> employeeBranchApiResponseDTOList = new ArrayList<>();
        EmployeeBranchApiResponseDTO employeeBranchApiResponseDTO = new EmployeeBranchApiResponseDTO();
        employeeBranchApiResponseDTO.setEmployerDTO(null);
        employeeBranchApiResponseDTO.setBranchDTO(null);
        employeeBranchApiResponseDTOList.add(employeeBranchApiResponseDTO);
        return employeeBranchApiResponseDTOList;
    }

    /**
     * Retrieves the manager of a branch by its ID.
     *
     * @param branchId The ID of the branch.
     * @return EmployerAllDetailsDTO containing the details of the manager.
     * @throws RuntimeException If the manager is not found for the specified branch ID.
     */
    @Override
    public EmployerAllDetailsDTO getManagerByBranchId(int branchId) {
        return employerRepository.findByBranchIdAndRole(branchId, Role.MANAGER)
                .map(this::convertToEmployerAllDetailsDTO)
                .orElse(null);

    }
}