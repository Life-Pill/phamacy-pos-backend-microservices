package com.pmj.authentication_service.config;

import com.pmj.authentication_service.entity.UserCredential;
import com.pmj.authentication_service.repository.UserCredentialRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> credential = repository.findByUsername(username);
        if (credential.isPresent()) {
            return new CustomerUserDetails(credential.get());
        } else {
            throw new UsernameNotFoundException("User not found with name: " + username);
        }
    }

}