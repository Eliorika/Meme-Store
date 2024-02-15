package dev.zekfad.rsreu.carservice.dto;

import java.time.Instant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Value;

public enum CarDTO {
	;
	private interface Id {
		@Positive
		Long getId();
	}

	private interface NumberPlate {
		@NotBlank(message = "Number plate must be filled.")
		String getNumberPlate();
	}

	private interface ArrivedAt {
		@PastOrPresent(message = "Arrival data must be in past or preset.")
		@NotNull
		Instant getArrivedAt();
	}

	private interface DepartedAt {
		@PastOrPresent(message = "Departure date must be in past or present.")
		Instant getDepartedAt();
	}

	private interface ServicePointId {
		@NotNull
		Long getServicePointId();
	}

	public enum Request {
		;
		@Value
		public static class Create implements NumberPlate, ArrivedAt, DepartedAt, ServicePointId {
			private String numberPlate;
			private Instant arrivedAt;
			private Instant departedAt;
			private Long servicePointId;
		}
	}

	public enum Response {
		;
		@Value
		public static class Public implements Id, NumberPlate, ArrivedAt, DepartedAt, ServicePointId {
			private Long id;
			private String numberPlate;
			private Instant arrivedAt;
			private Instant departedAt;
			private Long servicePointId;
		}

		@Value
		public static class Detached implements Id, NumberPlate, ArrivedAt, DepartedAt {
			private Long id;
			private String numberPlate;
			private Instant arrivedAt;
			private Instant departedAt;
		}
	}
}
