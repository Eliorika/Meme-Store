package dev.zekfad.rsreu.carservice.exception;

public class DataIntegrityException extends APIException {
	public DataIntegrityException(String message) {
		super(message, ErrorCode.INTEGRITY_ERROR);
	}
}
