package dev.zekfad.rsreu.carservice.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.zekfad.rsreu.carservice.entity.Role;
import dev.zekfad.rsreu.carservice.entity.User;
import dev.zekfad.rsreu.carservice.repository.RoleRepository;
import dev.zekfad.rsreu.carservice.repository.UserRepository;
import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class UserService implements UserDetailsService {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("User \"" + username + "\" not found");
		}

		return user;
	}

	public Optional<User> findUserById(@Nonnull Long id) {
		return userRepository.findById(id);
	}

	public boolean deleteUser(@Nonnull Long userId) {
		if (userRepository.findById(userId).isPresent()) {
			userRepository.deleteById(userId); // race condition?
			return true;
		}
		return false;
	}

	public User createUser(User user) {
		var existing = userRepository.findByUsername(user.getUsername());

		if (existing != null) {
			return null;
		}

		user.setRoles(Set.of(Role.ROLE_USER));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public List<User> allUsers() {
		return userRepository.findAll();
	}
}
