package dev.zekfad.rsreu.carservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.zekfad.rsreu.carservice.dto.APIError;
import dev.zekfad.rsreu.carservice.exception.ErrorCode;

@RestControllerAdvice
public class UsernameNotFoundAdvice {
	@ExceptionHandler(UsernameNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	APIError employeeNotFoundHandler(UsernameNotFoundException exception) {
		return new APIError(ErrorCode.AUTH_FAILED, exception);
	}
}
