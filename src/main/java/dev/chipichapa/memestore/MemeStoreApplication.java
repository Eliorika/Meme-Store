package dev.chipichapa.memestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


@ConfigurationPropertiesScan
@SpringBootApplication
public class MemeStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemeStoreApplication.class, args);
	}

}
