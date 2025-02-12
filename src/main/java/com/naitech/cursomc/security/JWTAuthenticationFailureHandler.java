package com.naitech.cursomc.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");

		Map<String, Object> data = new HashMap<>();
		data.put("timestamp", System.currentTimeMillis());
		data.put("status", HttpServletResponse.SC_UNAUTHORIZED);
		data.put("error", "Unauthorized");
		data.put("message", exception.getMessage());
		data.put("path", "/api/v1/auth");

		response.getOutputStream().write(objectMapper.writeValueAsBytes(data));
	}
}