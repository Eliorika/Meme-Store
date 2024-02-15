package dev.zekfad.rsreu.carservice.exception;

public class RoleNotFoundException extends NotFoundException {
	public RoleNotFoundException(long id) {
		super("Could not find role " + id);
	}
}
