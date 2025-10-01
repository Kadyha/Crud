package com.example.crudapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/static/**", "/", "/index.html").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login();
        // OAuth2 login enabled (Google, GitHub, etc.)
        return http.build();
    }
}
