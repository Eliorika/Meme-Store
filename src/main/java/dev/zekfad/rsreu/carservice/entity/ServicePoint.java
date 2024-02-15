package dev.zekfad.rsreu.carservice.entity;

import java.util.List;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ServicePoint {
	@Id
	@GeneratedValue
	@Nonnull
	private Long id;

	@Nonnull
	private String address;

	@OneToMany(mappedBy = "servicePoint")
	@Nonnull
	private List<Car> cars;
}
