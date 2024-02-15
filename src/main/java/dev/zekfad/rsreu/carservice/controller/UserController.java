package dev.zekfad.rsreu.carservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import dev.zekfad.rsreu.carservice.dto.UserDTO;
import dev.zekfad.rsreu.carservice.exception.UserExistsException;
import dev.zekfad.rsreu.carservice.mapper.UserMapper;
import dev.zekfad.rsreu.carservice.repository.UserRepository;
import dev.zekfad.rsreu.carservice.service.JwtService;
import dev.zekfad.rsreu.carservice.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UserController {
	@Autowired
	private UserService service;

	@Autowired
	private UserRepository repository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/admin/users")
	@PreAuthorize("hasAnyRole('ADMIN')")
	List<UserDTO.Response.Public> adminUsersAll() {
		return repository.findAll()
				.stream()
				.map(UserMapper.INSTANCE::userToPublic)
				.toList();
	}

	@PostMapping("/sign_up")
	public UserDTO.Response.Public signUp(@RequestBody @Valid UserDTO.Request.Create newUserDto) {
		var user = UserMapper.INSTANCE.createToUser(newUserDto);
		var newUser = service.createUser(user);
		if (newUser == null)
			throw new UserExistsException(user.getUsername());
		return UserMapper.INSTANCE.userToPublic(newUser);
	}

	@PostMapping("/generate_token")
	public String generateToken(@RequestBody @Valid UserDTO.Request.AuthRequest authRequest) {
		var authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUsername());
		} else {
			throw new UsernameNotFoundException("Invalid username or password");
		}
	}

}
