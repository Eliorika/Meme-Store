package dev.zekfad.rsreu.carservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import dev.zekfad.rsreu.carservice.dto.UserDTO;
import dev.zekfad.rsreu.carservice.entity.User;

@Mapper(componentModel = "default", uses = { RoleMapper.class })
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	UserDTO.Response.Public userToPublic(User entity);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "roles", expression = "java(java.util.Set.of())")
	@Mapping(target = "authorities", ignore = true)
	User createToUser(UserDTO.Request.Create dto);
}
