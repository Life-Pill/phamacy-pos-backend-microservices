package com.lifePill.SupplierService.controller;

import com.lifePill.SupplierService.dto.SupplierAndSupplierCompanyDTO;
import com.lifePill.SupplierService.dto.SupplierDTO;
import com.lifePill.SupplierService.service.SupplierService;
import com.lifePill.SupplierService.util.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing suppliers.
 */
@RestController
@RequestMapping("/lifepill/v1/supplier")
@AllArgsConstructor
public class SupplierController {

    private SupplierService supplierService;

    /**
     * Retrieves all suppliers.
     *
     * @return ResponseEntity containing a list of all suppliers.
     */
    @GetMapping(path = "/get-all-suppliers")
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        List<SupplierDTO> suppliers = supplierService.getAllSuppliers();
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }


    /**
     * Saves a new supplier.
     *
     * @param supplierDTO The supplier DTO to be saved.
     * @return ResponseEntity containing the saved supplier DTO.
     */
    @PostMapping(path = "/save")
    public ResponseEntity<SupplierDTO> saveSupplier(@RequestBody SupplierDTO supplierDTO) {
        SupplierDTO savedSupplier = supplierService.saveSupplier(supplierDTO);
        return new ResponseEntity<>(savedSupplier, HttpStatus.CREATED);
    }

    /**
     * Updates a supplier's details by their ID.
     *
     * @param id The ID of the supplier to update.
     * @param updatedSupplierDTO The updated SupplierDTO containing new details.
     * @return ResponseEntity containing the updated SupplierDTO with HTTP status OK if successful, or BAD_REQUEST if the update fails.
     */

    @PutMapping(path ="/update-supplier/{id}")
    public ResponseEntity<SupplierDTO> updateSupplierById(@PathVariable("id") long id, @RequestBody SupplierDTO updatedSupplierDTO) {
        SupplierDTO updatedSupplier = supplierService.updateSupplierById(id, updatedSupplierDTO);
        return new ResponseEntity<>(updatedSupplier, HttpStatus.OK);
    }

    /**
     * Retrieves a supplier by their ID.
     *
     * @param id The ID of the supplier to retrieve.
     * @return ResponseEntity containing the retrieved SupplierDTO with HTTP status OK if found, or NOT_FOUND if not found.
     */
    @GetMapping(path ="get-supplier/{id}")
    public ResponseEntity<SupplierDTO> getSupplierById(@PathVariable("id") long id) {
        SupplierDTO supplierDTO = supplierService.getSupplierById(id);
        return new ResponseEntity<>(supplierDTO, HttpStatus.OK);
    }

    /**
     * Retrieves a supplier and supplier company by their SupplierID.
     *
     * @param supplierId The ID of the supplier to retrieve.
     * @return ResponseEntity containing the retrieved SupplierDTO with HTTP status OK if found, or NOT_FOUND if not found.
     */
    @GetMapping(path ="get-supplier-with-company/{supplierId}")
    public ResponseEntity<SupplierAndSupplierCompanyDTO> getSupplierAndCompanyDetailsBySupplierId(
            @PathVariable("supplierId") long supplierId
    ) {
        SupplierAndSupplierCompanyDTO supplierAndSupplierCompanyDTO = supplierService
                .getSupplierAndCompanyBySupplierId(supplierId);
        return new ResponseEntity<>(supplierAndSupplierCompanyDTO, HttpStatus.OK);
    }

    /**
     * Deletes a supplier by their ID.
     *
     * @param id The ID of the supplier to delete.
     * @return ResponseEntity with HTTP status NO_CONTENT if the deletion is successful, or BAD_REQUEST if the deletion fails.
     */
    @DeleteMapping(path = "/delete-supplier/{id}")
    public ResponseEntity<Void> deleteSupplierById(@PathVariable("id") long id) {
        supplierService.deleteSupplierById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Checks if a supplier exists by their ID.
     *
     * @param supplierId The ID of the supplier to check.
     * @return ResponseEntity containing a StandardResponse with HTTP status OK.
     */
    @GetMapping(path = "/check-supplier-exists-by-id/{supplierId}")
    public ResponseEntity<StandardResponse> checkSupplierExistsById(
            @RequestParam(value = "supplierId") long supplierId
         ) {
        boolean exists = supplierService.checkSupplierExistsById(supplierId);
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        exists? "Supplier exists" : "Supplier does not exist",
                        exists
                ),
                HttpStatus.OK
        );
    }
}
