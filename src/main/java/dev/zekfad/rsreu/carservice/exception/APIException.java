package dev.zekfad.rsreu.carservice.exception;

import lombok.Getter;

abstract public class APIException extends RuntimeException {
	@Getter
	protected ErrorCode errorCode;

	protected APIException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	protected APIException(String message) {
		super(message);
		this.errorCode = ErrorCode.GENERIC_ERROR;
	}
}
