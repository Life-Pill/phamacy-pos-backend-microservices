package com.pmj.authentication_service.controller;

import com.pmj.authentication_service.dto.AuthRequest;
import com.pmj.authentication_service.entity.UserCredential;
import com.pmj.authentication_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential user) {
        return service.saveUser(user);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );
            if (authenticate.isAuthenticated()) {
                System.out.println("User authenticated: " + authRequest.getUsername());
                return service.generateToken(authRequest.getUsername());
            } else {
                System.out.println("User not authenticated: " + authRequest.getUsername());
                throw new RuntimeException("Invalid access");
            }
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            throw new RuntimeException("Invalid access");
        }
    }



    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }
}