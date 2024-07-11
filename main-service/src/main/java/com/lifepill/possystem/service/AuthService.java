package com.lifepill.possystem.service;

import com.lifepill.possystem.dto.requestDTO.AuthenticationRequestDTO;
import com.lifepill.possystem.dto.requestDTO.RegisterRequestDTO;
import com.lifepill.possystem.dto.responseDTO.AuthenticationResponseDTO;
import com.lifepill.possystem.dto.responseDTO.EmployerAuthDetailsResponseDTO;

public interface AuthService {

    AuthenticationResponseDTO register(RegisterRequestDTO registerRequest);
    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);
    EmployerAuthDetailsResponseDTO getEmployerDetails(String username);
}