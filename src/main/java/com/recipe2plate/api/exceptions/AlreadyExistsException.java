package com.recipe2plate.api.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
@Getter
public class AlreadyExistsException extends RuntimeException {
    private final String statusMessage;

    public AlreadyExistsException(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
