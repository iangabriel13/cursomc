package com.naitech.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.naitech.cursomc.services.DBService;
import com.naitech.cursomc.services.EmailService;
import com.naitech.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbService;

    @Bean
    boolean instantiateDatabase() throws ParseException {
		dbService.instantiateDatabase();
		return true;
	}
    
    @Bean
    public EmailService emailService() {
    	return new MockEmailService();
    }
}
