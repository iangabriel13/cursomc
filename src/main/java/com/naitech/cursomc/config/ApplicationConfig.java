package com.naitech.cursomc.config;

import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.naitech.cursomc.domain.Client;
import com.naitech.cursomc.repositories.ClientRepository;
import com.naitech.cursomc.security.User;

@Configuration
public class ApplicationConfig {

	private final ClientRepository clientRepository;

	public ApplicationConfig(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Bean
	UserDetailsService userDetailsService() {

		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				Client client = clientRepository.findByEmail(username);
				if (client == null) {
					throw new UsernameNotFoundException("User not found");
				}
				return (UserDetails) User.builder().id(client.getId()).email(client.getEmail())
						.password(client.getPassword()).authorities(client.getRoles().stream()
								.map(r -> new SimpleGrantedAuthority(r.getDescription())).collect(Collectors.toList()))
						.build();
			}
		};

	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
