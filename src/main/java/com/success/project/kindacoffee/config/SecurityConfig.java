package com.success.project.kindacoffee.config;

import com.success.project.kindacoffee.services.people.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final MvcRequestMatcher.Builder mvc;

    public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder, MvcRequestMatcher.Builder mvc) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.mvc = mvc;
    }

    @Bean
    @Order(0)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers(mvc.pattern("/login"))
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .formLogin(form -> form.defaultSuccessUrl("/persons")
                        .permitAll());
        return http.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain stylesFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request.requestMatchers(new AntPathRequestMatcher("/webjars**", "GET"))
                .permitAll()
                .anyRequest()
                .authenticated());
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

}