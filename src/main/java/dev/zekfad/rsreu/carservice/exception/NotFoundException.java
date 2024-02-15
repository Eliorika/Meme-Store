package dev.zekfad.rsreu.carservice.exception;

abstract public class NotFoundException extends APIException {
	protected NotFoundException(String message) {
		super(message);
		this.errorCode = ErrorCode.NOT_FOUND;
	}
}
