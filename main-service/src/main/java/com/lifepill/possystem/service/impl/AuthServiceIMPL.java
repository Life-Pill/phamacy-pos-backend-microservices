package com.lifepill.possystem.service.impl;

import com.lifepill.possystem.config.JwtService;
import com.lifepill.possystem.dto.EmployerDTO;
import com.lifepill.possystem.dto.requestDTO.AuthenticationRequestDTO;
import com.lifepill.possystem.dto.requestDTO.RegisterRequestDTO;
import com.lifepill.possystem.dto.responseDTO.AuthenticationResponseDTO;
import com.lifepill.possystem.dto.responseDTO.EmployerAuthDetailsResponseDTO;
import com.lifepill.possystem.entity.Branch;
import com.lifepill.possystem.entity.Employer;
import com.lifepill.possystem.exception.AuthenticationException;
import com.lifepill.possystem.exception.EntityDuplicationException;
import com.lifepill.possystem.exception.NotFoundException;
import com.lifepill.possystem.repo.branchRepository.BranchRepository;
import com.lifepill.possystem.repo.employerRepository.EmployerRepository;
import com.lifepill.possystem.service.AuthService;
import com.lifepill.possystem.service.EmployerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service implementation for handling authentication operations.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceIMPL implements AuthService {

    private  final EmployerRepository employerRepository;

    private final JwtService jwtService;

    private final BranchRepository branchRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final EmployerService employerService;

    /**
     * Registers a new employer.
     *
     * @param registerRequest The registration request containing employer details.
     * @return The authentication response containing the generated JWT token.
     */
    public AuthenticationResponseDTO register(RegisterRequestDTO registerRequest) {
        if (employerRepository.existsById(registerRequest.getEmployerId()) ||
                employerRepository.existsAllByEmployerEmail(registerRequest.getEmployerEmail())) {
            throw new EntityDuplicationException("Employer already exists");
        } else {
            Branch branch = branchRepository.findById(registerRequest.getBranchId())
                    .orElseThrow(() -> new NotFoundException("Branch not found with ID: "
                            + registerRequest.getBranchId()));

            // Encode the password before saving
            String encodedPassword = passwordEncoder.encode(registerRequest.getEmployerPassword());

            var employer = modelMapper.map(registerRequest, Employer.class);
            employer.setBranch(branch);
            employer.setEmployerPassword(encodedPassword); // Set the encoded password

            var savedEmployer = employerRepository.save(employer);
            String jwtToken = jwtService.generateToken(savedEmployer);

            return AuthenticationResponseDTO.builder().accessToken(jwtToken).build();
        }
    }

    /**
     * Authenticates an employer.
     *
     * @param request The authentication request containing employer credentials.
     * @return The authentication response containing the generated JWT token.
     * @throws AuthenticationException If authentication fails due to incorrect credentials.
     */
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        try {
            // Authenticate user using Spring Security's authenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmployerEmail(),
                            request.getEmployerPassword()
                    )
            );

            // If authentication is successful, generate JWT token
            var user = employerRepository.findByEmployerEmail(request.getEmployerEmail())
                    .orElseThrow(() -> new AuthenticationException("User not found"));
            String jwtToken = jwtService.generateToken(user);

            System.out.println(user.getBranch().getBranchId());



            // Return the authentication response containing the token
            return AuthenticationResponseDTO.builder().accessToken(jwtToken).build();
        } catch (org.springframework.security.core.AuthenticationException e) {
            // Authentication failed due to incorrect username or password
            throw new AuthenticationException("Incorrect username or password", e);
        }
    }

    /**
     * Retrieves the details of an employer for authentication purposes.
     * This method retrieves the details of the employer with the given username and constructs
     * an authentication response DTO containing the employer details.
     *
     * @param username The username of the employer.
     * @return An EmployerAuthDetailsResponseDTO containing the details of the employer.
     */
    @Override
    public EmployerAuthDetailsResponseDTO getEmployerDetails(String username) {
        // Retrieve employer details DTO using EmployerService
        EmployerDTO employerDTO = employerService.getEmployerByUsername(username);

        var user = employerRepository.findByEmployerEmail(username)
                .orElseThrow(() -> new AuthenticationException("User not found"));


        // set branch id
        employerDTO.setBranchId(user.getBranch().getBranchId());
        // Convert EmployerDTO to EmployerAuthDetailsResponseDTO using ModelMapper
        EmployerAuthDetailsResponseDTO employerDetailsResponseDTO = modelMapper
                .map(employerDTO, EmployerAuthDetailsResponseDTO.class);

        System.out.println("Branch ID retrieved: " + employerDTO.getBranchId());
        employerDetailsResponseDTO.setActiveStatus(true);

    /*    // Set activeStatus based on whether the user is logged in or not
        if (isLoggedIn(username)) {
            employerDetailsResponseDTO.setActiveStatus(true);
        } else {
            employerDetailsResponseDTO.setActiveStatus(false);
        }
*/
        return employerDetailsResponseDTO;

    }

/*    private boolean isLoggedIn(String username) {
        // Retrieve the currently authenticated user from Spring Security context
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Check if the principal is an instance of UserDetails (indicating the user is authenticated)
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            return userDetails.getUsername().equals(username);
        } else {
            return false; // User is not authenticated
        }
    }*/
}