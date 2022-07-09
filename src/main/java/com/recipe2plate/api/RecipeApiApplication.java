package com.recipe2plate.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class RecipeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipeApiApplication.class, args);
    }

}
