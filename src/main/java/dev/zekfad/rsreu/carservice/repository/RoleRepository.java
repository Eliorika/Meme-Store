package dev.zekfad.rsreu.carservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.zekfad.rsreu.carservice.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
