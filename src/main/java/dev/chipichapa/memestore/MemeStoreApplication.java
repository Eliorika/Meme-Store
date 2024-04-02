package dev.chipichapa.memestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@ConfigurationPropertiesScan
@EnableJpaRepositories
@EnableWebMvc
@EnableScheduling
public class MemeStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemeStoreApplication.class, args);
	}

}
