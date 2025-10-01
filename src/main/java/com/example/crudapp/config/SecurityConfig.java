package com.example.crudapp.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    private final ObjectProvider<ClientRegistrationRepository> clientRegistrations;

    private final Environment env;

    public SecurityConfig(ObjectProvider<ClientRegistrationRepository> clientRegistrations, Environment env) {
        this.clientRegistrations = clientRegistrations;
        this.env = env;
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            @Value("${security.oauth2.enabled:true}") boolean oauth2Enabled
    ) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.cors(cors -> {});

        boolean hasOauthClients = clientRegistrations.getIfAvailable() != null;
        boolean isLocal = Arrays.asList(env.getActiveProfiles()).contains("local");

        if (oauth2Enabled && hasOauthClients) {
            http
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/static/**", "/", "/index.html", "/oauth2/**", "/login/**", "/api/auth/me").permitAll()
                    .anyRequest().authenticated()
                )
                .oauth2Login();
        } else if (isLocal) {
            // Local dev: protect API with Basic Auth, allow others, enable H2 and SOAP
            http
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/static/**", "/", "/index.html", "/ws/**", "/h2-console/**", "/oauth2/**", "/login/**", "/api/auth/me").permitAll()
                    .requestMatchers("/api/**").authenticated()
                    .anyRequest().permitAll()
                )
                .httpBasic();
            // H2 console frames
            http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
        } else {
            // Fallback when no OAuth2 clients configured (e.g., Railway without secrets)
            http
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/static/**", "/", "/index.html", "/ws/**", "/h2-console/**", "/oauth2/**", "/login/**", "/api/auth/me").permitAll()
                    .anyRequest().permitAll()
                );
        }

        return http.build();
    }

    @Bean
    @Profile("local")
    public UserDetailsService inMemoryUsers(
            @Value("${app.security.user.name:dev}") String username,
            @Value("${app.security.user.password:dev123}") String password
    ) {
        // {noop} to avoid requiring a PasswordEncoder for dev only
        UserDetails user = User.withUsername(username)
                .password("{noop}" + password)
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of(
        "http://localhost:5173",
        "https://crud-cohan.vercel.app"
    ));
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization","Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
