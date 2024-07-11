package com.lifePill.posbranchservice.controller;

import com.lifePill.posbranchservice.dto.BranchDTO;
import com.lifePill.posbranchservice.dto.BranchUpdateDTO;
import com.lifePill.posbranchservice.service.BranchService;
import com.lifePill.posbranchservice.util.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * BranchController is a REST controller class that handles branch-related HTTP requests.
 * It uses the BranchService to perform the business logic related to branches.
 */
@RestController
@RequestMapping("lifepill/v1/branch")
@AllArgsConstructor
public class BranchController {

    private BranchService branchService;

    /**
     * This endpoint is used to save a new branch.
     * It accepts a multipart file as the branch image and a BranchDTO object as the branch details.
     * It returns a response entity with a standard response containing the status code, message, and the saved branch details.
     */
    @PostMapping(value = "/save-branch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StandardResponse> saveBranch(@RequestParam("image") MultipartFile image, BranchDTO branchDTO) {
        branchService.saveBranch(branchDTO, image);
        return new ResponseEntity<>(
                new StandardResponse(
                        201,
                        "Branch has been successfully saved",
                        branchDTO
                ),
                HttpStatus.CREATED
        );
    }

    /**
     * This endpoint is used to retrieve the image of a branch by its id.
     * It returns a response entity with the image data if found, otherwise it returns a not found status.
     */
    @GetMapping("/view-image/{branchId}")
    public ResponseEntity<byte[]> viewImage(@PathVariable int branchId) {
        byte[] imageData = branchService.getImageData(branchId);

        if (imageData != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This endpoint is used to retrieve all branches.
     * It returns a response entity with a standard response containing the status code, message, and the list of all branches.
     */
    @GetMapping(path = "/get-all-branches")
    public ResponseEntity<StandardResponse> getAllBranches() {
        List<BranchDTO> allBranches = branchService.getAllBranches();
        return new ResponseEntity<>(
                new StandardResponse(
                        201,
                        "Successfully retrieve all branches",
                        allBranches
                ),
                HttpStatus.OK
        );
    }

    /**
     * This endpoint is used to retrieve a branch by its id.
     * It returns a response entity with a standard response containing the status code, message, and the branch details.
     */
    @GetMapping(path = "/get-branch-by-id", params = "branchId")
    @Transactional
    public ResponseEntity<StandardResponse> getBranchById(@RequestParam(value = "branchId") int branchId) {
        BranchDTO branchDTO = branchService.getBranchById(branchId);
        return new ResponseEntity<>(
                new StandardResponse(
                        201,
                        "Successfully retrieved branch by id",
                        branchDTO
                ),
                HttpStatus.OK
        );
    }

    /**
     * This endpoint is used to delete a branch by its id.
     * It returns a response entity with a standard response containing the status code, message, and the id of the deleted branch.
     */
    @DeleteMapping(path = "/delete-branch/{id}")
    public ResponseEntity<StandardResponse> deleteBranch(@PathVariable(value = "id") int branchId) {
        String deleted = branchService.deleteBranch(branchId);
        return new ResponseEntity<>(
                new StandardResponse(
                        201,
                        "Branch id :" + branchId + ", Branch has been successfully deleted",
                        deleted
                ),
                HttpStatus.OK
        );
    }

    /**
     * This endpoint is used to update a branch by its id.
     * It accepts a multipart file as the new branch image and a BranchUpdateDTO object as the new branch details.
     * It returns a response entity with a standard response containing the status code, message, and the updated branch details.
     */
    @PutMapping(value = "/update-branch/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ResponseEntity<StandardResponse> updateBranch(
            @PathVariable(value = "id") int branchId,
            @RequestParam("image") MultipartFile image,
            BranchUpdateDTO branchUpdateDTO
    ) {
        branchService.updateBranch(branchId, branchUpdateDTO, image);
        return new ResponseEntity<>(
                new StandardResponse(
                        201,
                        "Branch id :" + branchId + ", Branch has been successfully updated",
                        branchUpdateDTO
                ),
                HttpStatus.OK
        );
    }

    /**
     * Endpoint for updating the image of a branch.
     *
     * @param branchId The ID of the branch to update the image for
     * @param image    The updated image file of the branch
     * @return A message indicating that the branch image has been updated
     */
    @PutMapping(value = "/update-image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ResponseEntity<StandardResponse> updateBranchImage(
            @PathVariable(value = "id") int branchId,
            @RequestParam("image") MultipartFile image) {
        branchService.updateBranchImage(branchId, image);
        return new ResponseEntity<>(
                new StandardResponse(
                        201,
                        "Branch id :" + branchId + ", Branch image has been successfully updated",
                        image
                ),
                HttpStatus.OK
        );
    }

    /**
     * Endpoint for updating a branch without updating its image.
     *
     * @param branchId       The ID of the branch to be updated
     * @param branchUpdateDTO The DTO containing updated branch details
     * @return A message indicating that the branch has been updated
     */
    @PutMapping(value = "/update/{branchId}")
    @Transactional
    public ResponseEntity<StandardResponse> updateBranchWithoutImage(
            @PathVariable(value = "branchId") int branchId,
            @ModelAttribute BranchUpdateDTO branchUpdateDTO) {
        branchService.updateBranchWithoutImage(branchId, branchUpdateDTO);
        return new ResponseEntity<>(
                new StandardResponse(
                        201,
                        "Branch id :" + branchId + ", Branch has been successfully updated",
                        branchUpdateDTO
                ),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/check-branch-exits-by-id", params = "branchId")
    public ResponseEntity<StandardResponse> checkBranchExistsById(
            @RequestParam(value = "branchId") int branchId
    ) {
        boolean exists = branchService.checkBranchExistsById(branchId);
        String message = exists ? "Branch exists" : "Branch does not exist";
        return new ResponseEntity<>(
                new StandardResponse(
                        201,
                        message,
                        exists
                ),
                HttpStatus.OK
        );
    }
}
