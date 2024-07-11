package com.lifepill.possystem.controller;// EmployerControllerTest.java
import com.lifepill.possystem.dto.*;
import com.lifepill.possystem.dto.requestDTO.EmployerUpdate.*;
import com.lifepill.possystem.entity.EmployerBankDetails;
import com.lifepill.possystem.exception.NotFoundException;
import com.lifepill.possystem.service.EmployerService;
import com.lifepill.possystem.util.StandardResponse;
import com.lifepill.possystem.util.mappers.EmployerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The type Employer controller test.
 */
class EmployerControllerTest {

    @Mock
    private EmployerService employerService;

    @InjectMocks
    private EmployerController employerController;

    @Mock
    private EmployerMapper employerMapper;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employerController = new EmployerController(employerService, employerMapper);
    }

    /**
     * Test save cashier without image.
     */
    @Test
    void testSaveCashierWithoutImage() {
        EmployerWithoutImageDTO employerWithoutImageDTO = new EmployerWithoutImageDTO();
        when(employerService.saveEmployerWithoutImage(employerWithoutImageDTO)).thenReturn("successfully saved");

        ResponseEntity<StandardResponse> responseEntity = employerController.saveCashierWithoutImage(employerWithoutImageDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(201, responseEntity.getBody().getCode());
        assertEquals("successfully saved", responseEntity.getBody().getMessage());
        assertEquals(employerWithoutImageDTO, responseEntity.getBody().getData());
    }

    /**
     * Test update employer.
     */
    @Test
    void testUpdateEmployer() {
        long employerId = 1;
        EmployerAllDetailsUpdateDTO employerAllDetailsUpdateDTO = new EmployerAllDetailsUpdateDTO();
        when(employerService.updateEmployer(employerId, employerAllDetailsUpdateDTO)).thenReturn("Employer updated successfully");

        ResponseEntity<StandardResponse> responseEntity = employerController.updateEmployer(employerId, employerAllDetailsUpdateDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(201, responseEntity.getBody().getCode());
        assertEquals("Employer updated successfully", responseEntity.getBody().getMessage());
        assertNull(responseEntity.getBody().getData());
    }

    /**
     * Test update employer account details.
     */
    @Test
    void testUpdateEmployerAccountDetails() {
        EmployerUpdateAccountDetailsDTO employerUpdateAccountDetailsDTO = new EmployerUpdateAccountDetailsDTO();
        when(employerService.updateEmployerAccountDetails(employerUpdateAccountDetailsDTO)).thenReturn("Successfully Update employer account details");

        ResponseEntity<StandardResponse> responseEntity = employerController.updateEmployerAccountDetails(employerUpdateAccountDetailsDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(201, responseEntity.getBody().getCode());
        assertEquals("Successfully Update employer account details", responseEntity.getBody().getMessage());
        assertNull(responseEntity.getBody().getData());
    }

    /**
     * Test get employer by id.
     */
    @Test
    void testGetEmployerById() {
        int employerId = 1;
        EmployerDTO employerDTO = new EmployerDTO();
        when(employerService.getEmployerById(employerId)).thenReturn(employerDTO);

        EmployerDTO result = employerController.getEmployerById(employerId);

        assertEquals(employerDTO, result);
    }

    /**
     * Test delete employer.
     */
    @Test
    void testDeleteEmployer() {
        int employerId = 1;
        when(employerService.deleteEmployer(employerId)).thenReturn("deleted succesfully : " + employerId);

        ResponseEntity<StandardResponse> responseEntity = employerController.deleteEmployer(employerId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(201, responseEntity.getBody().getCode());
        assertEquals("deleted succesfully : " + employerId, responseEntity.getBody().getMessage());
        assertNull(responseEntity.getBody().getData());
    }

    /**
     * Test get all employers.
     */
    @Test
    void testGetAllEmployers() {
        List<EmployerDTO> employerDTOList = Arrays.asList(new EmployerDTO(), new EmployerDTO());
        when(employerService.getAllEmployer()).thenReturn(employerDTOList);

        ResponseEntity<StandardResponse> responseEntity = employerController.getAllEmployers();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(201, responseEntity.getBody().getCode());
        assertEquals("SUCCESS", responseEntity.getBody().getMessage());
        assertEquals(employerDTOList, responseEntity.getBody().getData());
    }

    /**
     * Test get all employer by active state.
     */
    @Test
    void testGetAllEmployerByActiveState() {
        boolean activeState = true;
        List<EmployerDTO> employerDTOList = Arrays.asList(new EmployerDTO(), new EmployerDTO());
        when(employerService.getAllEmployerByActiveState(activeState)).thenReturn(employerDTOList);

        List<EmployerDTO> result = employerController.getAllEmployerByActiveState(activeState);

        assertEquals(employerDTOList, result);
    }

    /**
     * Test get all employer bank details.
     */
    @Test
    void testGetAllEmployerBankDetails() {
        List<EmployerUpdateBankAccountDTO> employerUpdateBankAccountDTOList = Arrays.asList(new EmployerUpdateBankAccountDTO(), new EmployerUpdateBankAccountDTO());
        when(employerService.getAllEmployerBankDetails()).thenReturn(employerUpdateBankAccountDTOList);

        ResponseEntity<StandardResponse> responseEntity = employerController.getAllEmployerBankDetails();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(201, responseEntity.getBody().getCode());
        assertEquals("SUCCESS", responseEntity.getBody().getMessage());
        assertEquals(employerUpdateBankAccountDTOList, responseEntity.getBody().getData());
    }

    /**
     * Test update employer bank account details with id success.
     */
    @Test
    void testUpdateEmployerBankAccountDetailsWithId_Success() {
        // Arrange
        long employerId = 1L;
        EmployerUpdateBankAccountDTO employerUpdateBankAccountDTO = new EmployerUpdateBankAccountDTO();
        EmployerWithBankDTO expectedEmployerWithBankDTO = new EmployerWithBankDTO();
        EmployerBankDetailsDTO expectedBankDetailsDTO = new EmployerBankDetailsDTO();
        EmployerBankDetails bankDetails = new EmployerBankDetails();

        when(employerService.updateEmployerBankAccountDetails(employerUpdateBankAccountDTO)).thenReturn(expectedEmployerWithBankDTO);
        when(employerService.getEmployerBankDetailsById(employerId)).thenReturn(expectedBankDetailsDTO);


        // Act
        ResponseEntity<StandardResponse> responseEntity = employerController.updateEmployerBankAccountDetailsWithId(employerId, employerUpdateBankAccountDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        StandardResponse response = responseEntity.getBody();
      assertEquals(201, response.getCode());
       assertEquals("SUCCESS", response.getMessage());
        EmployerWithBankDTO actualEmployerWithBankDTO = (EmployerWithBankDTO) response.getData();
       assertEquals(expectedEmployerWithBankDTO, actualEmployerWithBankDTO);
    }

    /**
     * Test update employer bank account details with id employer not found.
     */
    @Test
    void testUpdateEmployerBankAccountDetailsWithId_EmployerNotFound() {
        // Arrange
        long employerId = 1L;
        EmployerUpdateBankAccountDTO employerUpdateBankAccountDTO = new EmployerUpdateBankAccountDTO();
        String errorMessage = "Employer not found with ID: " + employerId;

        when(employerService.updateEmployerBankAccountDetails(employerUpdateBankAccountDTO)).thenThrow(new NotFoundException(errorMessage));

        // Act
        ResponseEntity<StandardResponse> responseEntity = employerController.updateEmployerBankAccountDetailsWithId(employerId, employerUpdateBankAccountDTO);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        StandardResponse response = responseEntity.getBody();
       assertEquals(404, response.getCode());
       assertEquals(errorMessage, response.getMessage());
    }
}