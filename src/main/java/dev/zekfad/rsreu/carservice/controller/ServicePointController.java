package dev.zekfad.rsreu.carservice.controller;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.zekfad.rsreu.carservice.dto.CarDTO;
import dev.zekfad.rsreu.carservice.dto.ServicePointDTO;
import dev.zekfad.rsreu.carservice.exception.DataIntegrityException;
import dev.zekfad.rsreu.carservice.exception.ServicePointNotFoundException;
import dev.zekfad.rsreu.carservice.mapper.CarMapper;
import dev.zekfad.rsreu.carservice.mapper.ServicePointMapper;
import dev.zekfad.rsreu.carservice.repository.ServicePointRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ServicePointController {
	private final ServicePointRepository repository;

	@GetMapping("/service-points")
	List<ServicePointDTO.Response.Public> all() {
		return repository.findAll()
				.stream()
				.map(ServicePointMapper.INSTANCE::servicePointToPublic)
				.toList();
	}

	@PostMapping("/service-points")
	ServicePointDTO.Response.Public newServicePoint(
			@RequestBody @Valid ServicePointDTO.Request.Create newServicePointDto) {
		var car = ServicePointMapper.INSTANCE.createToServicePoint(newServicePointDto);
		var newServicePoint = repository.save(car);
		return ServicePointMapper.INSTANCE.servicePointToPublic(newServicePoint);
	}

	@GetMapping("/service-points/{id}")
	ServicePointDTO.Response.Public one(@PathVariable @Positive Long id) {
		return repository.findById(id)
				.map(ServicePointMapper.INSTANCE::servicePointToPublic)
				.orElseThrow(
						() -> new ServicePointNotFoundException(id));
	};

	@PutMapping("/service-points/{id}")
	ServicePointDTO.Response.Public replaceServicePoint(
			@RequestBody @Valid ServicePointDTO.Request.Create newServicePointDto,
			@PathVariable @Positive Long id) {
		return repository.findById(id)
				.map(servicePoint -> {
					servicePoint.setAddress(newServicePointDto.getAddress());
					return repository.save(servicePoint);
				})
				.map(ServicePointMapper.INSTANCE::servicePointToPublic)
				.orElseThrow(
						() -> new ServicePointNotFoundException(id));
	}

	@DeleteMapping("/service-points/{id}")
	void deleteServicePoint(@PathVariable @Positive Long id) {
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Service point with id " + id + " have dependencies.");
		}
	}

	@GetMapping("/service-points/{id}/cars")
	List<CarDTO.Response.Detached> getCars(@PathVariable @Positive Long id) {
		return repository.findById(id)
				.map(servicePoint -> servicePoint.getCars())
				.map(CarMapper.INSTANCE::carMapDetached)
				.orElseThrow(
						() -> new ServicePointNotFoundException(id));
	}
}
