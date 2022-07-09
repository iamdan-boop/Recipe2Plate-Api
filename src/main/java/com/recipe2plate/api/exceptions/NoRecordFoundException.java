package com.recipe2plate.api.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class NoRecordFoundException extends RuntimeException {

    private final String statusMessage;

    public NoRecordFoundException(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
