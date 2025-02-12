package com.naitech.cursomc.security.auth;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.ServletException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	private final AuthenticationService authenticationService;
	private final AuthenticationFailureHandler authenticationFailureHandler;

	public AuthenticationController(AuthenticationService authenticationService,
			AuthenticationFailureHandler authenticationFailureHandler) {
		this.authenticationService = authenticationService;
		this.authenticationFailureHandler = authenticationFailureHandler;
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		try {
			return ResponseEntity.ok(authenticationService.authenticate(request));
		} catch (AuthenticationException e) {
			try {
				ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
						.getRequestAttributes();
				authenticationFailureHandler.onAuthenticationFailure(null, servletRequestAttributes.getResponse(), e);
			} catch (IOException | ServletException ex) {
				throw new RuntimeException(ex);
			}
			return null;
		}
	}
	
	@PostMapping("/forgot")
	public ResponseEntity<Void> forgot(@RequestBody AuthenticationRequest request){
		authenticationService.sendNewPassword(request.getEmail());
		
		return ResponseEntity.noContent().build();
	}
}
