package dev.zekfad.rsreu.carservice.entity;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {
	public static final Role ROLE_USER = new Role(1L, "USER", Set.of());

	@Id
	@GeneratedValue
	@Nonnull
	private Long id;

	@Nonnull
	private String name;
	
	@Transient
	@ManyToMany(mappedBy = "roles")
	@Nonnull
	private Set<User> users;

	@Override
	public String getAuthority() {
		return getName();
	}
}
