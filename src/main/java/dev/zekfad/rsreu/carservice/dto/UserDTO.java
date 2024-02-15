package dev.zekfad.rsreu.carservice.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Value;

public enum UserDTO {
	;
	private interface Id {
		@Positive
		Long getId();
	}

	private interface Name {
		@NotBlank(message = "Name must be filled.")
		String getName();
	}

	private interface Username {
		@NotBlank(message = "Username must be filled.")
		String getUsername();
	}

	private interface Password {
		@NotBlank(message = "Password must be filled.")
		String getPassword();
	}

	private interface Roles {
		Set<String> getRoles();
	}

	public enum Request {
		;
		@Value
		public static class Create implements Name, Username, Password {
			private String name;
			private String username;
			private String password;
		}

		@Value
		public static class AuthRequest implements Username, Password {
			private String username;
			private String password;
		}
	}

	public enum Response {
		;
		@Value
		public static class Public implements Id, Username, Roles {
			private Long id;
			private String username;
			private Set<String> roles;
		}
	}
}
