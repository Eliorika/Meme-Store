package dev.zekfad.rsreu.carservice.handler;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.zekfad.rsreu.carservice.dto.APIError;
import dev.zekfad.rsreu.carservice.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		var apiError = new APIError(ErrorCode.AUTH_FAILED, accessDeniedException);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		var out = response.getOutputStream();
		var mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter().writeValue(out, apiError);
		out.flush();
	}
}
