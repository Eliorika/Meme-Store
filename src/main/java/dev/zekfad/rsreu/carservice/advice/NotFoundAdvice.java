package dev.zekfad.rsreu.carservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.zekfad.rsreu.carservice.dto.APIError;
import dev.zekfad.rsreu.carservice.exception.ErrorCode;
import dev.zekfad.rsreu.carservice.exception.NotFoundException;

@RestControllerAdvice
public class NotFoundAdvice {
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	APIError employeeNotFoundHandler(NotFoundException exception) {
		return new APIError(ErrorCode.NOT_FOUND, exception);
	}
}
