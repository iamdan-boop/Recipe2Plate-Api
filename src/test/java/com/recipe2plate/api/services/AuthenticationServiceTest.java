package com.recipe2plate.api.services;

import com.github.javafaker.Faker;
import com.recipe2plate.api.dto.request.auth.LoginRequest;
import com.recipe2plate.api.dto.request.auth.SignupRequest;
import com.recipe2plate.api.entities.AppUser;
import com.recipe2plate.api.entities.Role;
import com.recipe2plate.api.exceptions.AlreadyExistsException;
import com.recipe2plate.api.exceptions.BadCredentialsException;
import com.recipe2plate.api.repositories.AppUserRepository;
import com.recipe2plate.api.repositories.RoleRepository;
import com.recipe2plate.api.security.JwtTokenUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    private AuthenticationService authenticationService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder =
            new BCryptPasswordEncoder();

    private final Faker faker = new Faker();

    private final String password = faker.internet().password();

    private AppUser appUser;

    private SignupRequest signupRequest;
    private Role role;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void beforeEach() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        appUser = AppUser.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password(bCryptPasswordEncoder.encode(password)).build();

        role = new Role(2L, "USER");

        signupRequest = new SignupRequest(appUser.getFirstName(), appUser.getLastName(), appUser.getEmail(), appUser.getPassword());

        authenticationService = new AuthenticationService(
                appUserRepository,
                roleRepository,
                bCryptPasswordEncoder,
                jwtTokenUtil);
    }


    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void should_return_authToken_if_credentials_are_correct() {
        when(jwtTokenUtil.generateToken(any()))
                .thenReturn("authTokenLogin");

        when(appUserRepository.findByEmail(appUser.getEmail())).thenReturn(Optional.of(appUser));

        final String authToken = authenticationService
                .authenticate(new LoginRequest(appUser.getEmail(), password));

        verify(appUserRepository, times(1)).findByEmail(appUser.getEmail());

        assertThat(authToken).isEqualTo("authTokenLogin");
    }


    @Test
    void should_throw_exception_if_credentials_are_incorrect() {
        when(appUserRepository.findByEmail(appUser.getEmail())).thenReturn(Optional.of(appUser));

        BadCredentialsException invalidCredentials = assertThrows(BadCredentialsException.class, () -> {
            authenticationService.authenticate(new LoginRequest(appUser.getEmail(), password + "incorrect"));
        });

        verify(appUserRepository, times(1)).findByEmail(appUser.getEmail());

        assertThat(invalidCredentials.getStatusMessage()).contains("Invalid Credentials");
    }


    @Test
    void should_return_authToken_if_credentials_are_non_duplicated_and_valid_in_registration() {

        when(jwtTokenUtil.generateToken(any()))
                .thenReturn("authTokenRegister");

        when(appUserRepository.findByEmail(appUser.getEmail()))
                .thenReturn(Optional.empty());

        when(roleRepository.findById(role.getId()))
                .thenReturn(Optional.of(role));


        appUser.setRole(role);

        when(appUserRepository.save(any(AppUser.class))).thenReturn(appUser);

        final String authToken = authenticationService.signUp(signupRequest);


        verify(appUserRepository, times(1)).findByEmail(appUser.getEmail());

        verify(roleRepository, times(1)).findById(role.getId());


        assertThat(authToken).isEqualTo("authTokenRegister");
    }


    @Test
    void should_throw_already_exists_exception_if_email_is_already_taken() {
        when(appUserRepository.findByEmail(appUser.getEmail())).thenThrow(new AlreadyExistsException("Email already exists"));

        final AlreadyExistsException emailAlreadyExists = assertThrows(AlreadyExistsException.class, () ->
                authenticationService.signUp(signupRequest));

        verify(appUserRepository, times(1)).findByEmail(appUser.getEmail());
        assertThat(emailAlreadyExists.getStatusMessage()).isEqualTo("Email already exists");
    }
}