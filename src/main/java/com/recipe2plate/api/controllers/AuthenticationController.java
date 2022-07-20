package com.recipe2plate.api.controllers;


import com.recipe2plate.api.dto.request.auth.SignupRequest;
import com.recipe2plate.api.dto.response.AuthTokenDto;
import com.recipe2plate.api.entities.AppUser;
import com.recipe2plate.api.security.UserAuthenticationProvider;
import com.recipe2plate.api.services.AuthenticationService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserAuthenticationProvider userAuthenticationProvider;
    private final AuthenticationService authenticationService;

    @PostMapping("/signIn")
    public ResponseEntity<AuthTokenDto> signIn(@ApiIgnore @AuthenticationPrincipal AppUser appUser) {
        final String authToken = userAuthenticationProvider.generateToken(appUser.getEmail());
        return ResponseEntity.ok().body(new AuthTokenDto(authToken));
    }


    @PostMapping("/signUp")
    public ResponseEntity<AuthTokenDto> signUp(@Valid @RequestBody SignupRequest signupRequest) {
        final String authToken = userAuthenticationProvider.generateToken(
                authenticationService.signUp(signupRequest).getEmail()
        );
        return ResponseEntity.ok().body(new AuthTokenDto(authToken));
    }
}
