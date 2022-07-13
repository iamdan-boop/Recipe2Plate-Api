package com.recipe2plate.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class RecipeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipeApiApplication.class, args);
    }

}
