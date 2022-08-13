package com.recipe2plate.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe2plate.api.dto.request.auth.LoginRequest;
import com.recipe2plate.api.dto.request.auth.SignupRequest;
import com.recipe2plate.api.services.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AuthenticationControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    public AuthenticationController authenticationController;

    @MockBean
    public AuthenticationService authenticationService;

    private final ObjectMapper objectMapper = new ObjectMapper();


    private final SignupRequest signupRequest = SignupRequest.builder()
            .email("iamdan@gmail.com")
            .firstName("Dan Janus")
            .lastName("Pineda")
            .password("iamdan123")
            .build();


    private final LoginRequest loginRequest = new LoginRequest(
            signupRequest.getEmail(),
            signupRequest.getPassword()
    );

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }


    @Test
    void signUp_should_return_auth_token_if_success() throws Exception {
        when(authenticationService.signUp(any()))
                .thenReturn("authToken1234");

        mockMvc.perform(post("/signUp")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(signupRequest))
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.authToken").value("authToken1234"));
    }


    @Test
    void signUp_should_throw_error_when_fields_are_missing() throws Exception {
        signupRequest.setPassword("");
        mockMvc.perform(post("/signUp")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsBytes(signupRequest))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void signIn_should_return_authToken_when_credentials_are_correct() throws Exception {
        when(authenticationService.authenticate(any()))
                .thenReturn("authTokenLogin");

        mockMvc.perform(post("/signIn")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(loginRequest))
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.authToken").value("authTokenLogin"));
    }


    @Test
    void signIn_should_throw_error_when_fields_are_missing() throws Exception {
        loginRequest.setPassword("");
        mockMvc.perform(post("/signIn")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(loginRequest))
        ).andExpect(status().isBadRequest());
    }
}