package dev.zekfad.rsreu.carservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.zekfad.rsreu.carservice.entity.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
}
