package com.success.project.kindacoffee.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(PasswordEncoderConfiguration.class.getName());

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.debug("Bcrypt encoder created");
        return new BCryptPasswordEncoder();
    }
}
