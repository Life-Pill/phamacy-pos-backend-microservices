package com.pmj.authentication_service.service.Impl;

import com.pmj.authentication_service.entity.UserCredential;
import com.pmj.authentication_service.repository.UserCredentialRepository;
import com.pmj.authentication_service.service.AuthService;
import com.pmj.authentication_service.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserCredentialRepository userCredentialRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    public String saveUser(UserCredential userCredential) {
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        userCredentialRepository.save(userCredential);
        return "User saved successfully";
    }

    public String generateToken(String userName) {
        return jwtService.generateToken(userName);
    }

    public void validateToken(final String token) {
        jwtService.validateToken(token);
    }
}
