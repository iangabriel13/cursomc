package com.naitech.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.naitech.cursomc.services.DBService;
import com.naitech.cursomc.services.EmailService;
import com.naitech.cursomc.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

    @Bean
    boolean instantiateDatabase() throws ParseException {
    	if(!strategy.equalsIgnoreCase("create")) {
    		return false;
    	}
    	
		dbService.instantiateDatabase();
		return true;
	}
    
    @Bean
    public EmailService emailService() {
    	return new SmtpEmailService();
    }
}
