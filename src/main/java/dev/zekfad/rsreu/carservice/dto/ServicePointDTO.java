package dev.zekfad.rsreu.carservice.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

public enum ServicePointDTO {
	;
	private interface Id {
		@Positive
		Long getId();
	}

	private interface Address {
		@NotBlank(message = "Address must be filled.")
		String getAddress();
	}

	public enum Request {
		;
		@Value
		@Jacksonized @Builder
		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class Create implements Address {
			private String address;
		}
	}

	public enum Response {
		;
		@Value
		public static class Public implements Id, Address {
			private Long id;
			private String address;
		}
	}
}
