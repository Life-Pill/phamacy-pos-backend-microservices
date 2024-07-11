package com.lifePill.SupplierService.service.impl;


import com.lifePill.SupplierService.dto.SupplierAndSupplierCompanyDTO;
import com.lifePill.SupplierService.dto.SupplierCompanyDTO;
import com.lifePill.SupplierService.dto.SupplierDTO;
import com.lifePill.SupplierService.entity.Supplier;
import com.lifePill.SupplierService.entity.SupplierCompany;
import com.lifePill.SupplierService.exception.EntityDuplicationException;
import com.lifePill.SupplierService.exception.NotFoundException;
import com.lifePill.SupplierService.repository.SupplierCompanyRepository;
import com.lifePill.SupplierService.repository.SupplierRepository;
import com.lifePill.SupplierService.service.SupplierCompanyService;
import com.lifePill.SupplierService.service.SupplierService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Implementation of the SupplierService interface providing operations related to suppliers.
 */
@Service
@Transactional
@AllArgsConstructor
public class SupplierServiceIMPL implements SupplierService {

    private SupplierRepository supplierRepository;
    private SupplierCompanyRepository supplierCompanyRepository;
    private ModelMapper modelMapper;
    private SupplierCompanyService supplierCompanyService;

    /**
     * Retrieves all suppliers.
     *
     * @return A list of SupplierDTO objects representing all suppliers.
     * @throws NotFoundException If no suppliers are found.
     */
    public List<SupplierDTO> getAllSuppliers() {
        List<Supplier> getAllSuppliers = supplierRepository.findAll();

        if (!getAllSuppliers.isEmpty()){
            List<SupplierDTO> supplierDTOList = new ArrayList<>();
            for (Supplier supplier: getAllSuppliers){
                SupplierDTO supplierDTO = modelMapper.map(supplier, SupplierDTO.class);
                supplierDTO.setCompanyId(supplier.getSupplierCompany().getCompanyId());
                supplierDTOList.add(supplierDTO);
            }

            return supplierDTOList;
        }else {
            throw new NotFoundException("No Supplier Found");
        }
    }

    /**
     * Saves a new supplier or updates an existing one.
     *
     * @param supplierDTO The DTO containing supplier details to be saved.
     * @return The saved or updated SupplierDTO object.
     * @throws EntityDuplicationException If a supplier with the same ID or email already exists.
     * @throws NotFoundException         If the associated supplier company is not found with the provided ID.
     */
    public SupplierDTO saveSupplier(SupplierDTO supplierDTO) {
        if (supplierRepository.existsById(supplierDTO.getSupplierId()) || supplierRepository.existsAllBySupplierEmail(supplierDTO.getSupplierEmail())) {
            throw new EntityDuplicationException("Supplier already exists");
        } else {
            // Retrieve the Supplier Company entity by its ID
            SupplierCompany supplierCompany = supplierCompanyRepository.findById(supplierDTO.getCompanyId())
                    .orElseThrow(() -> new NotFoundException("Supplier Company not found with ID: " + supplierDTO.getCompanyId()));

            // Map EmployerDTO to Supplier entity
            Supplier supplier = modelMapper.map(supplierDTO, Supplier.class);

            // Set the Branch entity to the Supplier entity
            supplier.setSupplierCompany(supplierCompany);
            // Save the Supplier entity
            supplierRepository.save(supplier);

            // In return company id is not showing but it went to the database
            return modelMapper.map(supplier, SupplierDTO.class);
        }
    }

    /**
     * Updates an existing supplier by ID with the provided details.
     *
     * @param id                The ID of the supplier to be updated.
     * @param updatedSupplierDTO The DTO containing updated supplier details.
     * @return The updated SupplierDTO object.
     * @throws NotFoundException If no supplier is found with the given ID.
     */
    public SupplierDTO updateSupplierById(long id, SupplierDTO updatedSupplierDTO) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (optionalSupplier.isPresent()) {
            Supplier existingSupplier = optionalSupplier.get();
            // Update existingSupplier with values from updatedSupplierDTO
            existingSupplier.setSupplierName(updatedSupplierDTO.getSupplierName());
            existingSupplier.setSupplierAddress(updatedSupplierDTO.getSupplierAddress());
            existingSupplier.setSupplierPhone(updatedSupplierDTO.getSupplierPhone());
            existingSupplier.setSupplierEmail(updatedSupplierDTO.getSupplierEmail());
            existingSupplier.setSupplierDescription(updatedSupplierDTO.getSupplierDescription());
            existingSupplier.setSupplierImage(updatedSupplierDTO.getSupplierImage());
            existingSupplier.setSupplierRating(updatedSupplierDTO.getSupplierRating());

            Supplier savedSupplier = supplierRepository.save(existingSupplier);
            return modelMapper.map(savedSupplier, SupplierDTO.class);
        } else {
            throw new NotFoundException("Supplier not found with id: " + id);
        }
    }

    /**
     * Deletes a supplier by ID.
     *
     * @param id The ID of the supplier to be deleted.
     * @throws NotFoundException If no supplier is found with the given ID.
     */
    public void deleteSupplierById(long id) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (optionalSupplier.isPresent()) {
            supplierRepository.deleteById(id);
        } else {
            throw new NotFoundException("Supplier not found with id: " + id);
        }
    }

    /**
     * Retrieves a supplier by ID.
     *
     * @param id The ID of the supplier to retrieve.
     * @return The supplier DTO corresponding to the given ID.
     * @throws NotFoundException If no supplier is found with the given ID.
     */
    public SupplierDTO getSupplierById(long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Supplier not found with id: " + id));
        return modelMapper.map(supplier, SupplierDTO.class);
    }

    /**
     * Retrieves a Supplier and its associated SupplierCompany by the Supplier's ID.
     *
     * @param supplierId The ID of the Supplier to retrieve.
     * @return A SupplierAndSupplierCompanyDTO object containing the Supplier and its associated SupplierCompany.
     * @throws NotFoundException If no Supplier is found with the given ID or if no SupplierCompany is found with the ID retrieved from the Supplier.
     */
    @Override
    public SupplierAndSupplierCompanyDTO getSupplierAndCompanyBySupplierId(long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new NotFoundException("Supplier not found with id: " + supplierId));

        //model mappers
        SupplierDTO supplierDTO = modelMapper.map(supplier, SupplierDTO.class);

        long SupplierCompanyId = supplier.getSupplierCompany().getCompanyId();
        System.out.println(SupplierCompanyId);
        SupplierCompany supplierCompany = supplierCompanyRepository.findById(SupplierCompanyId)
                .orElseThrow(() -> new NotFoundException("Supplier Company not found"));
        System.out.println(supplierDTO);

        SupplierCompanyDTO supplierCompanyDTO = modelMapper.map(supplierCompany,SupplierCompanyDTO.class);

        SupplierAndSupplierCompanyDTO supplierAndSupplierCompanyDTO = new SupplierAndSupplierCompanyDTO();

        supplierAndSupplierCompanyDTO.setSupplierDTO(supplierDTO);
        supplierAndSupplierCompanyDTO.setSupplierCompanyDTO(supplierCompanyDTO);

        return supplierAndSupplierCompanyDTO;
    }

    /**
     * Checks if a Supplier exists by its ID.
     *
     * @param supplierId The ID of the Supplier to check.
     * @return A boolean indicating whether a Supplier with the given ID exists.
     */
    @Override
    public boolean checkSupplierExistsById(long supplierId) {
        return supplierRepository.existsById(supplierId);
    }
}