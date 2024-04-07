package com.sp1222.language.daybyday.api.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundByUsernameException.class)
    public ApiExceptionResponse handleNotFoundByUsernameException(NotFoundByUsernameException exception) {
        log.warn("Provided username not found.");
        return new ApiExceptionResponse(HttpStatus.NOT_FOUND, "Entity not found by Username: " + exception.getUsername());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundByNameException.class)
    public ApiExceptionResponse handleNotFoundByNameException(NotFoundByNameException exception) {
        log.warn("Provided entity name not found.");
        return new ApiExceptionResponse(HttpStatus.NOT_FOUND, "Entity not found by Name: " + exception.getName());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MisMatchedIdException.class)
    public ApiExceptionResponse handleMisMatchedIdException(MisMatchedIdException exception) {
        log.error("Mis-matched entity Ids.");
        return new ApiExceptionResponse(HttpStatus.BAD_REQUEST, "Provided Id " + exception.getId() + " does not match Entity Id " + exception.getEntityId());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicatePropertyException.class)
    public ApiExceptionResponse handleDuplicatePropertyException(DuplicatePropertyException exception) {
        log.error("Entity with given property already exists.");
        return new ApiExceptionResponse(HttpStatus.BAD_REQUEST, "Entity property already exists: " + exception.getProperty());
    }

    /**
     * Handles invalid argument exceptions thrown by @Valid.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("Invalid argument.");
        BindingResult bindingResult = exception.getBindingResult();
        FieldError fieldError = bindingResult.getFieldErrors().get(0);
        return new ApiExceptionResponse(HttpStatus.BAD_REQUEST, fieldError.getDefaultMessage() + ": " + fieldError.getRejectedValue());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ApiExceptionResponse handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        log.error("Data integrity violation exception.");
        return new ApiExceptionResponse(HttpStatus.BAD_REQUEST, "Data Integrity Violation Exception: " + exception.getMessage());
    }
}
