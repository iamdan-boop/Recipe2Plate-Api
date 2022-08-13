package com.recipe2plate.api.controllers;


import com.recipe2plate.api.dto.request.auth.LoginRequest;
import com.recipe2plate.api.dto.request.auth.SignupRequest;
import com.recipe2plate.api.dto.response.AuthTokenDto;
import com.recipe2plate.api.services.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthTokenDto> signIn(@Valid @RequestBody LoginRequest loginRequest) {
        final String authToken = authenticationService.authenticate(loginRequest);
        return ResponseEntity.ok().body(new AuthTokenDto(authToken));
    }


    @PostMapping("/signUp")
    public ResponseEntity<AuthTokenDto> signUp(@Valid @RequestBody SignupRequest signupRequest) {
        final String authToken = authenticationService.signUp(signupRequest);
        return ResponseEntity.ok().body(new AuthTokenDto(authToken));
    }
}
