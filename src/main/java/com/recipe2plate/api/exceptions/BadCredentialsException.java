package com.recipe2plate.api.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class BadCredentialsException extends RuntimeException {

   private final String statusMessage;

    public BadCredentialsException(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
