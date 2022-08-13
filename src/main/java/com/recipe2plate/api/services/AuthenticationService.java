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
import com.recipe2plate.api.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;


    public String authenticate(LoginRequest loginRequest) {
        final AppUser appUser = appUserRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NoRecordFoundException("Invalid Credentials"));
        if (!passwordEncoder.matches(loginRequest.getPassword(), appUser.getPassword())) {
            throw new BadCredentialsException("Invalid Credentials");
        }
        final Authentication authentication = new UsernamePasswordAuthenticationToken(
                appUser,
                null,
                List.of((GrantedAuthority) () -> (appUser.getRole().getRoleName()))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenUtil.generateToken(appUser.getEmail());
    }


    @Transactional
    public String signUp(SignupRequest signupRequest) {
        final Optional<AppUser> appUser = appUserRepository
                .findByEmail(signupRequest.getEmail());

        if (appUser.isPresent()) {
            throw new AlreadyExistsException("email already exists");
        }

        final Role appUserRole = roleRepository.findById(2L)
                .orElseThrow(() -> new NoRecordFoundException("Role doesnt seem to exists"));

        final AppUser newUser = AppUser.builder()
                .firstName(signupRequest.getFirstName())
                .lastName(signupRequest.getLastName())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .role(appUserRole)
                .build();

        appUserRepository.save(newUser);
        return jwtTokenUtil.generateToken(newUser.getEmail());
    }
}
