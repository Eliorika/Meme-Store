package dev.zekfad.rsreu.carservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.zekfad.rsreu.carservice.dto.CarDTO;
import dev.zekfad.rsreu.carservice.exception.CarNotFoundException;
import dev.zekfad.rsreu.carservice.mapper.CarMapper;
import dev.zekfad.rsreu.carservice.repository.CarRepository;
import dev.zekfad.rsreu.carservice.repository.ServicePointRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CarController {
	private final CarRepository repository;
	private final ServicePointRepository servicePointRepository;

	@GetMapping("/cars")
	List<CarDTO.Response.Public> all() {
		return repository.findAll()
				.stream()
				.map(CarMapper.INSTANCE::carToPublic)
				.toList();
	}

	@PostMapping("/cars")
	CarDTO.Response.Public newCar(@RequestBody @Valid CarDTO.Request.Create newCarDto) {
		var car = CarMapper.INSTANCE.createToCar(newCarDto, servicePointRepository);
		var newCar = repository.save(car);
		return CarMapper.INSTANCE.carToPublic(newCar);
	}

	@GetMapping("/cars/{id}")
	CarDTO.Response.Public one(@PathVariable @Positive Long id) {
		return repository.findById(id)
				.map(CarMapper.INSTANCE::carToPublic)
				.orElseThrow(
						() -> new CarNotFoundException(id));
	}

	@PutMapping("/cars/{id}")
	CarDTO.Response.Public replaceCar(
			@RequestBody @Valid CarDTO.Request.Create newCar,
			@PathVariable @Positive Long id) {
		return repository.findById(id)
				.map(car -> {
					car.setNumberPlate(newCar.getNumberPlate());
					car.setArrivedAt(newCar.getArrivedAt());
					car.setDepartedAt(newCar.getDepartedAt());
					return repository.save(car);
				})
				.map(CarMapper.INSTANCE::carToPublic)
				.orElseThrow(
						() -> new CarNotFoundException(id));
	}

	@DeleteMapping("/cars/{id}")
	void deleteCar(@PathVariable @Positive Long id) {
		repository.deleteById(id);
	}
}
