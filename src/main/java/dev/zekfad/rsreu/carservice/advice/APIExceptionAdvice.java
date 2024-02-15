package dev.zekfad.rsreu.carservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.zekfad.rsreu.carservice.dto.APIError;
import dev.zekfad.rsreu.carservice.exception.APIException;

@RestControllerAdvice
public class APIExceptionAdvice {
	@ExceptionHandler(APIException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	APIError employeeNotFoundHandler(APIException exception) {
		return new APIError(exception.getErrorCode(), exception);
	}
}
