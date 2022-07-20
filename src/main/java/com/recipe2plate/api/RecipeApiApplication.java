package com.recipe2plate.api;

import com.recipe2plate.api.entities.Category;
import com.recipe2plate.api.repositories.CategoryRepository;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class RecipeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipeApiApplication.class, args);
    }
}
