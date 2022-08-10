package com.recipe2plate.api.repositories;

import com.github.javafaker.Faker;
import com.recipe2plate.api.entities.AppUser;
import com.recipe2plate.api.exceptions.NoRecordFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository appUserRepository;


    private final Faker faker = new Faker();

    private AppUser appUser;

    @BeforeEach
    void beforeEach() {
        appUser = AppUser.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .build();

        appUserRepository.save(appUser);
    }

    @Test
    void should_return_app_user_when_found() {
        final AppUser findAppUser = appUserRepository.findByEmail(appUser.getEmail())
                .orElseThrow(() -> new NoRecordFoundException("No AppUser associated with email Found"));

        assertThat(findAppUser.getEmail()).isEqualTo(appUser.getEmail());
    }


    @Test
    void should_throw_exception_if_email_not_found() {
        assertThrows(NoRecordFoundException.class, () -> appUserRepository.findByEmail(faker.internet().emailAddress())
                .orElseThrow(() -> new NoRecordFoundException("No AppUser associated with email Found")));
    }
}