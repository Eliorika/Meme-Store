package dev.zekfad.rsreu.carservice.dto;

import dev.zekfad.rsreu.carservice.exception.ErrorCode;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

@Setter
@Getter
@Jacksonized
@Builder
public class APIError {
	public APIError(
			@Nonnull ErrorCode code,
			@Nonnull String message,
			@Nullable List<String> details) {
		this.code = code.code;
		this.message = message;
		this.details = details;
	}

	public APIError(@Nonnull ErrorCode code, @Nonnull Exception exception) {
		this.code = code.code;
		this.message = exception.getMessage();
		this.details = null;
	}

	@JsonCreator
	public APIError(@Nonnull int code, @Nonnull String message, List<String> details) {
		this.code = code;
		this.message = message;
		this.details = null;
	}

	@Nonnull
	final private int code;

	@Nonnull
	final private String message;

	@Nullable
	final private List<String> details;
}
