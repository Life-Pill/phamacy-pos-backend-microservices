package com.lifePill.posbranchservice.service.Impl;

import com.lifePill.posbranchservice.dto.BranchDTO;
import com.lifePill.posbranchservice.dto.BranchUpdateDTO;
import com.lifePill.posbranchservice.entity.Branch;
import com.lifePill.posbranchservice.exception.EntityDuplicationException;
import com.lifePill.posbranchservice.exception.NotFoundException;
import com.lifePill.posbranchservice.helper.SaveImageHelper;
import com.lifePill.posbranchservice.repository.BranchRepository;
import com.lifePill.posbranchservice.service.BranchService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BranchServiceIMPL implements BranchService {

    private BranchRepository branchRepository;
    private ModelMapper modelMapper;

    @Override
    public void saveBranch(BranchDTO branchDTO, MultipartFile image) {
        if (branchRepository.existsById(branchDTO.getBranchId()) || branchRepository.existsByBranchEmail(branchDTO.getBranchEmail())) {
            throw new EntityDuplicationException("Branch already exists for that id or email.");
        }

        Branch branch = convertToEntity(branchDTO);
        branch.setBranchImage(SaveImageHelper.saveImage(image));
        branchRepository.save(branch);
    }

    @Override
    public byte[] getImageData(int branchId) {
        return branchRepository.findById(branchId)
                .map(Branch::getBranchImage)
                .orElse(null);
    }

    @Override
    public List<BranchDTO> getAllBranches() {
        List<Branch> branches = branchRepository.findAll();
        if (branches.isEmpty()) {
            throw new NotFoundException("No Branch Found");
        }

        List<BranchDTO> branchDTOList = new ArrayList<>();
        for (Branch branch : branches) {
            branchDTOList.add(convertToDTO(branch));
        }
        return branchDTOList;
    }

    @Override
    public BranchDTO getBranchById(int branchId) {
        return branchRepository.findById(branchId)
                .map(this::convertToDTO)
                .orElseThrow(() -> new NotFoundException("No Branch found for that id"));
    }

    @Override
    public String deleteBranch(int branchId) {
        if (!branchRepository.existsById(branchId)) {
            throw new NotFoundException("No Branch found for that id");
        }

        branchRepository.deleteById(branchId);
        return "Deleted successfully: " + branchId;
    }

    @Override
    public String updateBranch(int branchId, BranchUpdateDTO branchUpdateDTO, MultipartFile image) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found"));

        updateBranchDetails(branch, branchUpdateDTO);
        if (image != null && !image.isEmpty()) {
            branch.setBranchImage(SaveImageHelper.saveImage(image));
        }
        branchRepository.save(branch);
        return "Updated";
    }

    @Override
    public void updateBranchImage(int branchId, MultipartFile image) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new NotFoundException("Branch not found"));

        if (image != null && !image.isEmpty()) {
            branch.setBranchImage(SaveImageHelper.saveImage(image));
        }
        branchRepository.save(branch);
    }

    @Override
    public void updateBranchWithoutImage(int branchId, BranchUpdateDTO branchUpdateDTO) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new NotFoundException("Branch not found"));

        updateBranchDetails(branch, branchUpdateDTO);
        branchRepository.save(branch);
    }

    @Override
    public boolean checkBranchExistsById(int branchId) {
        return branchRepository.existsById(branchId);
    }

    private BranchDTO convertToDTO(Branch branch) {
        return modelMapper.map(branch, BranchDTO.class);
    }

    private Branch convertToEntity(BranchDTO branchDTO) {
        return modelMapper.map(branchDTO, Branch.class);
    }

    private void updateBranchDetails(Branch branch, BranchUpdateDTO branchUpdateDTO) {
        if (branchUpdateDTO.getBranchName() != null) {
            branch.setBranchName(branchUpdateDTO.getBranchName());
        }
        if (branchUpdateDTO.getBranchAddress() != null) {
            branch.setBranchAddress(branchUpdateDTO.getBranchAddress());
        }
        if (branchUpdateDTO.getBranchContact() != null) {
            branch.setBranchContact(branchUpdateDTO.getBranchContact());
        }
        if (branchUpdateDTO.getBranchFax() != null) {
            branch.setBranchFax(branchUpdateDTO.getBranchFax());
        }
        if (branchUpdateDTO.getBranchEmail() != null) {
            branch.setBranchEmail(branchUpdateDTO.getBranchEmail());
        }
        if (branchUpdateDTO.getBranchDescription() != null) {
            branch.setBranchDescription(branchUpdateDTO.getBranchDescription());
        }
        if (branchUpdateDTO.getBranchLocation() != null) {
            branch.setBranchLocation(branchUpdateDTO.getBranchLocation());
        }
        if (branchUpdateDTO.getBranchCreatedOn() != null) {
            branch.setBranchCreatedOn(branchUpdateDTO.getBranchCreatedOn());
        }
        if (branchUpdateDTO.getBranchCreatedBy() != null) {
            branch.setBranchCreatedBy(branchUpdateDTO.getBranchCreatedBy());
        }
        branch.setBranchStatus(branchUpdateDTO.isBranchStatus());
    }
}
