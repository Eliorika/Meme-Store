package dev.zekfad.rsreu.carservice.exception;

public class ServicePointNotFoundException extends NotFoundException {
	public ServicePointNotFoundException(long id) {
		super("Could not find service point " + id);
	}
}
