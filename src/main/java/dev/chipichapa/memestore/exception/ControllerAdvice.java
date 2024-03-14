package dev.chipichapa.memestore.exception;

import dev.chipichapa.memestore.security.exception.AccessDeniedException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class ControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ExceptionBodyResponse handleResourceNotFoundException(ResourceNotFoundException e) {
        return constructResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AccessDeniedException.class,
            org.springframework.security.access.AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ExceptionBodyResponse handleAccessDeniedException() {
        return constructResponse("Access denied", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({AuthenticationException.class, BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ExceptionBodyResponse handleAuthentication(AuthenticationException e) {
        return constructResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ExceptionBodyResponse handleExceptions(Exception e) {
        return constructResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ExceptionBodyResponse constructResponse(Exception e, HttpStatus httpStatus) {
        logException(e);
        return constructResponse(e.getMessage(), httpStatus);
    }

    private ExceptionBodyResponse constructResponse(String message, HttpStatus httpStatus) {
        return new ExceptionBodyResponse(httpStatus.value(), message);
    }

    private static void logException(Exception e) {
        log.error("Error: ", e);
    }
}
