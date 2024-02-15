package dev.zekfad.rsreu.carservice.exception;

public enum ErrorCode {
	GENERIC_ERROR(100),
	NOT_FOUND(101),
	INVALID_ARGUMENT(102),
	INTEGRITY_ERROR(103),
	AUTH_FAILED(104),
	SIGN_UP_FAILED(105),
	;

	final public int code;

	private ErrorCode(int code) {
		this.code = code;
	}
}
