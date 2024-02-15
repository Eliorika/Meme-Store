package dev.zekfad.rsreu.carservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.zekfad.rsreu.carservice.dto.APIError;
import dev.zekfad.rsreu.carservice.exception.ErrorCode;
import jakarta.validation.ConstraintViolation;

@RestControllerAdvice
public class MethodArgumentNotValidAdvice {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	APIError employeeNotFoundHandler(MethodArgumentNotValidException exception) {
		var details = exception.getBindingResult().getAllErrors()
				.stream()
				.map(error -> error.unwrap(ConstraintViolation.class))
				.map(error -> String.format("%s: %s", error.getPropertyPath(), error.getMessage()));
		return new APIError(ErrorCode.INVALID_ARGUMENT, "Validation failed", details.toList());
	}
}
