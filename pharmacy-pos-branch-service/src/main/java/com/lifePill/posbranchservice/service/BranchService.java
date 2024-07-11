package com.lifePill.posbranchservice.service;

import com.lifePill.posbranchservice.dto.BranchDTO;
import com.lifePill.posbranchservice.dto.BranchUpdateDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BranchService {

    void saveBranch(BranchDTO branchDTO, MultipartFile image);

    byte[] getImageData(int branchId);

    List<BranchDTO> getAllBranches();

    BranchDTO getBranchById(int branchId);

    String deleteBranch(int branchId);

    String updateBranch(int branchId, BranchUpdateDTO branchUpdateDTO, MultipartFile image);

    void updateBranchImage(int branchId, MultipartFile image);

    void updateBranchWithoutImage(int branchId, BranchUpdateDTO branchUpdateDTO);

    boolean checkBranchExistsById(int branchId);
}
