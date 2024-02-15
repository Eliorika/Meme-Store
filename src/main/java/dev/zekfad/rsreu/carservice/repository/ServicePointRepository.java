package dev.zekfad.rsreu.carservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.zekfad.rsreu.carservice.entity.ServicePoint;

public interface ServicePointRepository extends JpaRepository<ServicePoint, Long> {
}
