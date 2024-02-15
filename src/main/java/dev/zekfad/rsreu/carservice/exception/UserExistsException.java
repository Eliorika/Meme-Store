package dev.zekfad.rsreu.carservice.exception;

public class UserExistsException extends APIException {
	public UserExistsException(String username) {
		super("User " + username + " already exists", ErrorCode.SIGN_UP_FAILED);
	}
}
