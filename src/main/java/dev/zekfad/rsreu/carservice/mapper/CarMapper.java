package dev.zekfad.rsreu.carservice.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import dev.zekfad.rsreu.carservice.dto.CarDTO;
import dev.zekfad.rsreu.carservice.entity.Car;
import dev.zekfad.rsreu.carservice.exception.ServicePointNotFoundException;
import dev.zekfad.rsreu.carservice.repository.ServicePointRepository;

@Mapper(componentModel = "default")
public interface CarMapper {
	CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

	@Mapping(target = "servicePointId", expression = "java(entity.getServicePoint().getId())")
	CarDTO.Response.Public carToPublic(Car entity);

	CarDTO.Response.Detached carToDetached(Car entity);
	List<CarDTO.Response.Detached> carMapDetached(List<Car> entity);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "servicePoint", ignore = true)
	Car createToCar(CarDTO.Request.Create dto, @Context ServicePointRepository servicePointRepository);

	@AfterMapping
	default void servicePointIdToServicePoint(
		@MappingTarget Car entity,
		CarDTO.Request.Create dto,
		@Context ServicePointRepository servicePointRepository
	) {
		var id = dto.getServicePointId();
		var servicePoint = servicePointRepository.findById(id)
			.orElseThrow(() -> new ServicePointNotFoundException(id));
		entity.setServicePoint(servicePoint);
	}
}
