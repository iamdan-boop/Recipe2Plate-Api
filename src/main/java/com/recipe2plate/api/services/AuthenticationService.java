package com.recipe2plate.api.services;


import com.recipe2plate.api.dto.request.auth.LoginRequest;
import com.recipe2plate.api.dto.request.auth.SignupRequest;
import com.recipe2plate.api.entities.AppUser;
import com.recipe2plate.api.entities.Role;
import com.recipe2plate.api.exceptions.AlreadyExistsException;
import com.recipe2plate.api.exceptions.BadCredentialsException;
import com.recipe2plate.api.exceptions.NoRecordFoundException;
import com.recipe2plate.api.repositories.AppUserRepository;
import com.recipe2plate.api.repositories.RoleRepository;
import com.recipe2plate.api.repositories.VerificationOtpRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;

    private final VerificationOtpRepository verificationOtpRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public AppUser authenticate(LoginRequest loginRequest) {
        final AppUser appUser = appUserRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NoRecordFoundException("Invalid Credentials"));
        log.info("appUser {}", appUser);
        if (!passwordEncoder.matches(loginRequest.getPassword(), appUser.getPassword())) {
            throw new BadCredentialsException("Invalid Credentials");
        }
        log.info("authenticateSuccess, {}", appUser);
        return appUser;
    }


    public AppUser findByEmail(String email) {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new NoRecordFoundException("User doesnt seem to exists"));
    }

    @Transactional
    public AppUser signUp(SignupRequest signupRequest) {
        final Optional<AppUser> appUser = appUserRepository
                .findByEmail(signupRequest.getEmail());

        if (appUser.isPresent()) {
            throw new AlreadyExistsException("email already exists");
        }

        final Role appUserRole = roleRepository.findById(2L)
                .orElseThrow(() -> new NoRecordFoundException("Role doesnt seem to exists"));

        final AppUser newUser = AppUser.builder()
                .firstName(signupRequest.getFirstName())
                .lastName(signupRequest.getPassword())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .role(appUserRole)
                .build();

        return appUserRepository.save(newUser);
    }
}
