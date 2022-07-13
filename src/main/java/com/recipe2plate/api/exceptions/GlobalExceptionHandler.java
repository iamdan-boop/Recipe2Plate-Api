package com.recipe2plate.api.exceptions;

import com.recipe2plate.api.dto.response.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {
        final Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((value) -> {
            errors.put(value.getField(), value.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(NoRecordFoundException.class)
    public ResponseEntity<ErrorDto> handleRecordNotFoundException(NoRecordFoundException ex) {
        final ErrorDto errorDto = new ErrorDto(ex.getStatusMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleBadCredentialsException(BadCredentialsException ex) {
        final ErrorDto errorDto = new ErrorDto(ex.getStatusMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handleAlreadyExistsException(AlreadyExistsException ex) {
        final ErrorDto errorDto = new ErrorDto(ex.getStatusMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDto);
    }
}
