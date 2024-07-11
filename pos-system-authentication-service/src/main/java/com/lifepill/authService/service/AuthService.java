package com.lifepill.authService.service;

import com.lifepill.authService.dto.requestDTO.AuthenticationRequestDTO;
import com.lifepill.authService.dto.requestDTO.RegisterRequestDTO;
import com.lifepill.authService.dto.responseDTO.AuthenticationResponseDTO;
import com.lifepill.authService.dto.responseDTO.EmployerAuthDetailsResponseDTO;

public interface AuthService {

    AuthenticationResponseDTO register(RegisterRequestDTO registerRequest);
    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);
    EmployerAuthDetailsResponseDTO getEmployerDetails(String username);
}