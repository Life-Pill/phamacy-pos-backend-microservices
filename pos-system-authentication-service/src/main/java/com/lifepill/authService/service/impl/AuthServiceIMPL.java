package com.lifepill.authService.service.impl;

import com.lifepill.authService.config.JwtService;
import com.lifepill.authService.dto.EmployerDTO;
import com.lifepill.authService.dto.requestDTO.AuthenticationRequestDTO;
import com.lifepill.authService.dto.requestDTO.RegisterRequestDTO;
import com.lifepill.authService.dto.responseDTO.AuthenticationResponseDTO;
import com.lifepill.authService.dto.responseDTO.EmployerAuthDetailsResponseDTO;
import com.lifepill.authService.entity.Employer;
import com.lifepill.authService.exception.AuthenticationException;
import com.lifepill.authService.exception.EntityDuplicationException;
import com.lifepill.authService.repo.EmployerRepository;
import com.lifepill.authService.service.AuthService;
import com.lifepill.authService.service.EmployerService;
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
            //TODO check if branch exists

            // Encode the password before saving
            String encodedPassword = passwordEncoder.encode(registerRequest.getEmployerPassword());

            var employer = modelMapper.map(registerRequest, Employer.class);

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

        // Convert EmployerDTO to EmployerAuthDetailsResponseDTO using ModelMapper
        EmployerAuthDetailsResponseDTO employerDetailsResponseDTO = modelMapper
                .map(employerDTO, EmployerAuthDetailsResponseDTO.class);

        System.out.println("Branch ID retrieved: " + employerDTO.getBranchId());
        employerDetailsResponseDTO.setActiveStatus(true);

        return employerDetailsResponseDTO;

    }

}