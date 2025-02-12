package com.naitech.cursomc.security.auth;

import java.security.SecureRandom;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.naitech.cursomc.config.JwtService;
import com.naitech.cursomc.domain.Client;
import com.naitech.cursomc.repositories.ClientRepository;
import com.naitech.cursomc.services.EmailService;
import com.naitech.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthenticationService {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
	
	private final UserDetailsService userDetailsService;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final ClientRepository clientRepository;
	private final PasswordEncoder passwordEncoder;
	private final EmailService emailService;

	public AuthenticationService(UserDetailsService userDetailsService, JwtService jwtService,
			AuthenticationManager authenticationManager, ClientRepository clientRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
		this.userDetailsService = userDetailsService;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
		this.clientRepository = clientRepository;
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());
		String jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}
	
	public void sendNewPassword(String email) {
		Client client = clientRepository.findByEmail(email);
		if(client == null) {
			throw new ObjectNotFoundException("Email not found");
		}
		
		String newPassword = generateRandomPassword(10);
		client.setPassword(passwordEncoder.encode(newPassword));
		
		clientRepository.save(client);
		emailService.sendNewPasswordEmail(client, newPassword);
		
	}

    public static String generateRandomPassword(int length) {
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            password.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }

}
