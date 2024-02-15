package dev.zekfad.rsreu.carservice.exception;

public class UserNotFoundException extends NotFoundException {
	public UserNotFoundException(long id) {
		super("Could not found user " + id);
	}
}
