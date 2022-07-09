package com.recipe2plate.api.controllers;


import com.recipe2plate.api.config.security.UserAuthenticationProvider;
import com.recipe2plate.api.dto.AuthTokenDto;
import com.recipe2plate.api.dto.request.SignupRequest;
import com.recipe2plate.api.entities.AppUser;
import com.recipe2plate.api.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserAuthenticationProvider userAuthenticationProvider;
    private final AuthenticationService authenticationService;

    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(@AuthenticationPrincipal AppUser appUser) {
        final String authToken = userAuthenticationProvider.generateToken(appUser.getEmail());
        return ResponseEntity.ok().body(authToken);
    }


    @PostMapping("/signUp")
    public ResponseEntity<AuthTokenDto> signUp(@Valid @RequestBody SignupRequest signupRequest) {
        final String authToken = userAuthenticationProvider.generateToken(
                authenticationService.signUp(signupRequest).getEmail()
        );
        return ResponseEntity.ok().body(new AuthTokenDto(authToken));
    }
}
