package dev.zekfad.rsreu.carservice.mapper;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import dev.zekfad.rsreu.carservice.entity.Role;

@Mapper(componentModel = "default")
public interface RoleMapper {
	RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "name", source = "value")
	@Mapping(target = "users", expression = "java(java.util.Set.of())")
	Role stringToRole(String value);

	default String roleToString(Role entity) {
		return entity.getName();
	}

	Set<Role> stringSetToRoleSet(Set<String> values);

	Set<String> roleSetToStringSet(Set<Role> values);
}
