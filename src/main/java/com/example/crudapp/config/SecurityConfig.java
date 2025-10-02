package com.example.crudapp.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
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
        @Value("${security.oauth2.enabled:true}") boolean oauth2Enabled,
    @Value("${app.frontend.url:/}") String frontendUrl
    ) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.cors(cors -> {});

        boolean hasOauthClients = clientRegistrations.getIfAvailable() != null;
    boolean isLocal = Arrays.asList(env.getActiveProfiles()).contains("local");
    boolean isDocker = Arrays.asList(env.getActiveProfiles()).contains("docker");

        if (oauth2Enabled && hasOauthClients) {
            // In cloud: use OAuth2 login and redirect back to frontend after success
            SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
            successHandler.setDefaultTargetUrl("/");
            successHandler.setAlwaysUseDefaultTargetUrl(true);

            // On OAuth2 failure, send the user back to the SPA home with an error flag
            AuthenticationFailureHandler failureHandler = (request, response, exception) -> {
                response.sendRedirect("/login?error");
            };

            http
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/static/**", "/", "/index.html", "/login", "/login.html", "/oauth2/**", "/login/**", "/login/oauth2/**", "/api/auth/me", "/api/auth/debug", "/api/health/**").permitAll()
                    .anyRequest().authenticated()
                )
                // Habilitar HTTP Basic para herramientas como Postman con usuario dev/dev123
                .httpBasic(basic -> {})
                // Usar /login como página única: incluye formulario y link a GitHub
                .oauth2Login(o -> o
                    .loginPage("/login")
                    .failureHandler(failureHandler)
                    .successHandler(successHandler)
                )
                .formLogin(form -> form
                    .loginPage("/login")
                    .loginProcessingUrl("/perform_login")
                    .successHandler(successHandler)
                    .failureUrl("/login?error")
                    .permitAll()
                )
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll());
        } else if (isLocal || isDocker) {
            // Local dev: protect API with form login, allow others, enable H2 and SOAP
            // Local dev: protect API with form login, allow others, enable H2 and SOAP
            // After successful login redirect to Vue dev server (frontendUrl)
            SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
            successHandler.setDefaultTargetUrl("/");
            successHandler.setAlwaysUseDefaultTargetUrl(true);

            http
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/static/**", "/", "/index.html", "/login", "/login.html", "/ws/**", "/h2-console/**", "/oauth2/**", "/login/**", "/api/auth/me", "/api/auth/debug", "/api/health/**").permitAll()
                    .requestMatchers("/api/**").authenticated()
                    .anyRequest().permitAll()
                )
                .formLogin(form -> form
                    .loginPage("/login")
                    .loginProcessingUrl("/perform_login")
                    .successHandler(successHandler)
                    .failureUrl("/login?error")
                    .permitAll()
                )
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll());
            // H2 console frames
            http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
        } else {
            // Fallback when no OAuth2 clients configured (e.g., Railway without secrets)
            // Protect API with form login and enable Basic for tools; keep login page at /login and process POSTs at /perform_login
            SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
            successHandler.setDefaultTargetUrl("/");
            successHandler.setAlwaysUseDefaultTargetUrl(true);

            http
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/static/**", "/", "/index.html", "/login", "/login.html", "/oauth2/**", "/login/**", "/api/auth/me", "/api/auth/debug", "/api/health/**").permitAll()
                    .requestMatchers("/api/**").authenticated()
                    .anyRequest().permitAll()
                )
                .httpBasic(basic -> {})
                .formLogin(form -> form
                    .loginPage("/login")
                    .loginProcessingUrl("/perform_login")
                    .successHandler(successHandler)
                    .failureUrl("/login?error")
                    .permitAll()
                )
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll());
        }

        return http.build();
    }

    @Bean
    @ConditionalOnProperty(name = "app.security.dev-user.enabled", havingValue = "true", matchIfMissing = true)
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
    // Use origin patterns to support Vercel preview deployments and Railway domain
    config.setAllowedOriginPatterns(List.of(
        "http://localhost:5173",
        "https://crud-cohan.vercel.app",
        "https://*.vercel.app",
        "https://*.railway.app",
        "https://crud-production-d05d.up.railway.app"
    ));
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization","Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
