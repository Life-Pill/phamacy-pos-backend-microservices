package com.pmj.authentication_service.service;

import com.pmj.authentication_service.entity.UserCredential;

public interface AuthService {

    String saveUser(UserCredential userCredential);

    String generateToken(String userName);

    void validateToken(final String token);
}
