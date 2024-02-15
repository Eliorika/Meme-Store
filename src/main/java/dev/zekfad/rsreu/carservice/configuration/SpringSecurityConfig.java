package dev.zekfad.rsreu.carservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dev.zekfad.rsreu.carservice.filter.JwtAuthFilter;
import dev.zekfad.rsreu.carservice.handler.AuthenticationDeniedHandler;
import dev.zekfad.rsreu.carservice.handler.AuthenticationFailedHandler;
import dev.zekfad.rsreu.carservice.handler.AuthenticationRequiredHandler;
import dev.zekfad.rsreu.carservice.service.UserService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	@Autowired
	private JwtAuthFilter authFilter;

	@Autowired
	public PasswordEncoder passwordEncoder;

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserService();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		var authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	AccessDeniedHandler accessDeniedHandler() {
		return new AuthenticationDeniedHandler();
	}

	@Bean
	AuthenticationEntryPoint authenticationEntryPoint() {
		return new AuthenticationRequiredHandler();
	}

	@Bean
	AuthenticationFailureHandler authenticationFailureHandler() {
		return new AuthenticationFailedHandler();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				// .csrf((csrf) -> csrf.disable())
				// .httpBasic((basic) -> basic.disable())
				// .formLogin((form) -> form.disable())
				// .sessionManagement((sessionManagement) -> sessionManagement
				// .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				// .exceptionHandling((exceptionHandling) -> exceptionHandling
				// .authenticationEntryPoint(new Http403ForbiddenEntryPoint()))
				.csrf(AbstractHttpConfigurer::disable)
				.cors(AbstractHttpConfigurer::disable)
				.sessionManagement((sessionManagement) -> sessionManagement
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling((exceptionHandling) -> exceptionHandling
						.accessDeniedHandler(accessDeniedHandler())
						.authenticationEntryPoint(authenticationEntryPoint()))
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/auth/sign_in", "/auth/sign_up", "/auth/generate_token").permitAll()
						.requestMatchers("/auth/user/**").authenticated()
						.requestMatchers("/auth/admin/**").hasAnyRole("ADMIN")
						.anyRequest().permitAll())
				// .formLogin(null)
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(authFilter,
						UsernamePasswordAuthenticationFilter.class)
				.build();
	}
}