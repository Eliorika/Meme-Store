package dev.zekfad.rsreu.carservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import dev.zekfad.rsreu.carservice.dto.ServicePointDTO;
import dev.zekfad.rsreu.carservice.entity.ServicePoint;

@Mapper(componentModel = "default")
public interface ServicePointMapper {
	ServicePointMapper INSTANCE = Mappers.getMapper(ServicePointMapper.class);

	ServicePointDTO.Response.Public servicePointToPublic(ServicePoint entity);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "cars", expression = "java(java.util.List.of())")
	ServicePoint createToServicePoint(ServicePointDTO.Request.Create dto);
}
