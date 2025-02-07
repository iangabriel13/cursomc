package com.naitech.cursomc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naitech.cursomc.domain.BankSlipPayment;
import com.naitech.cursomc.domain.CardPayment;

@Configuration
public class JacksonConfig {

	@Bean
	Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(CardPayment.class);
				objectMapper.registerSubtypes(BankSlipPayment.class);
				super.configure(objectMapper);
			};
		};
		return builder;
	}
}
