package dev.zekfad.rsreu.carservice.exception;

public class CarNotFoundException extends NotFoundException {
	public CarNotFoundException(long id) {
		super("Could not find car " + id);
	}
}
