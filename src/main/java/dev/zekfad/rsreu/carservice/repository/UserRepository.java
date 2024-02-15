package dev.zekfad.rsreu.carservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.zekfad.rsreu.carservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
