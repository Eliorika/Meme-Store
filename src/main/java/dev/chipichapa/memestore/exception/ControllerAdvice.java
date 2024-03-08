package dev.chipichapa.memestore.exception;

import dev.chipichapa.memestore.domain.dto.BasicApiResponse;
import dev.chipichapa.memestore.security.exception.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    BasicApiResponse<ExceptionBodyResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
        return constructResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AccessDeniedException.class,
            org.springframework.security.access.AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    BasicApiResponse<ExceptionBodyResponse> handleAccessDeniedException() {
        return constructResponse("Access denied", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    BasicApiResponse<ExceptionBodyResponse> handleAuthentication(AuthenticationException e) {
        return constructResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    BasicApiResponse<ExceptionBodyResponse> handleExceptions(Exception e) {
        return constructResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private BasicApiResponse<ExceptionBodyResponse> constructResponse(Exception e, HttpStatus httpStatus) {
        return constructResponse(e.getMessage(), httpStatus);
    }

    private BasicApiResponse<ExceptionBodyResponse> constructResponse(String message, HttpStatus httpStatus) {
        return new BasicApiResponse<>(
                true,
                new ExceptionBodyResponse(httpStatus.value(), message)
        );
    }


}
