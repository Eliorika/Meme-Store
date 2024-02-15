package dev.zekfad.rsreu.carservice.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.zekfad.rsreu.carservice.dto.APIError;
import dev.zekfad.rsreu.carservice.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFailedHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		var apiError = new APIError(ErrorCode.AUTH_FAILED, exception);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		var out = response.getOutputStream();
		var mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter().writeValue(out, apiError);
		out.flush();
	}
}
