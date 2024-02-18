package dev.chipichapa.memestore.controller;

import dev.chipichapa.memestore.exception.ExceptionBodyResponse;
import dev.chipichapa.memestore.exception.ResourceNotFoundException;
import dev.chipichapa.memestore.security.exception.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ExceptionBodyResponse handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ExceptionBodyResponse(e.getMessage());
    }

    @ExceptionHandler({AccessDeniedException.class,
            org.springframework.security.access.AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ExceptionBodyResponse handleAccessDeniedException() {
        return new ExceptionBodyResponse("Access denied");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ExceptionBodyResponse handleExceptions(Exception e) {
        return new ExceptionBodyResponse(e.getMessage());
    }

}
