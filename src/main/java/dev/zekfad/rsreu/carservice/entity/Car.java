package dev.zekfad.rsreu.carservice.entity;

import java.time.Instant;

import jakarta.annotation.Nullable;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Car {
	@Id
	@GeneratedValue
	@Nonnull
	private Long id;

	@Nonnull
	private String numberPlate;

	@Nonnull
	private Instant arrivedAt;

	@Nullable
	private Instant departedAt;

	@ManyToOne(optional = false)
	@JoinColumn(name = "service_point_id", referencedColumnName = "id")
	@Nonnull
	private ServicePoint servicePoint;

	boolean isInService() {
		return departedAt != null;
	}
}
