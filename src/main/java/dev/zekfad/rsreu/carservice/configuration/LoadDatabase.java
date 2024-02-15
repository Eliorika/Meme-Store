package dev.zekfad.rsreu.carservice.configuration;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.zekfad.rsreu.carservice.entity.Car;
import dev.zekfad.rsreu.carservice.entity.Role;
import dev.zekfad.rsreu.carservice.entity.ServicePoint;
import dev.zekfad.rsreu.carservice.entity.User;
import dev.zekfad.rsreu.carservice.repository.CarRepository;
import dev.zekfad.rsreu.carservice.repository.RoleRepository;
import dev.zekfad.rsreu.carservice.repository.ServicePointRepository;
import dev.zekfad.rsreu.carservice.repository.UserRepository;

@Configuration
class LoadDatabase {
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	CommandLineRunner initDatabase(
			CarRepository carRepository,
			ServicePointRepository servicePointRepository,
			UserRepository userRepository,
			RoleRepository roleRepository) {
		return args -> {
			var userRole = roleRepository.save(new Role(1l, "ROLE_USER", Set.of()));
			log.info("Preloading " + userRole);
			var adminRole = roleRepository.save(new Role(2l, "ROLE_ADMIN", Set.of()));
			log.info("Preloading " + adminRole);

			var adminPassword = passwordEncoder.encode("admin");
			var admin = userRepository.save(new User(
					1l,
					"admin",
					"admin",
					adminPassword,
					Set.of(adminRole)));
			log.info("Preloading " + admin);

			var servicePoint = servicePointRepository.save(new ServicePoint(
					1l,
					"Moscow",
					List.of()));
			log.info("Preloading " + servicePoint);
			log.info("Preloading " + carRepository.save(
					new Car(
							1l,
							"х777хх777",
							Instant.ofEpochSecond(1695190948),
							null,
							servicePoint)));
			log.info("Preloading " + carRepository.save(
					new Car(
							2l,
							"х888хх88",
							Instant.ofEpochSecond(1695194948),
							null,
							servicePoint)));
		};
	}
}