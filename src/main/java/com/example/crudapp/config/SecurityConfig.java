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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
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

    private String effectiveFrontendUrl(String configured) {
        if (configured != null && (configured.startsWith("http://") || configured.startsWith("https://"))) {
            return configured;
        }
        return "/"; // relative root keeps user on same origin (works with Vercel and Docker)
    }

    private boolean isVercelRequest(HttpServletRequest request) {
        String xfh = request.getHeader("X-Forwarded-Host");
        String fwd = request.getHeader("Forwarded");
        String referer = request.getHeader("Referer");
        String origin = request.getHeader("Origin");
        String server = request.getServerName();

        if (xfh != null && (xfh.equals("crud-cohan.vercel.app") || xfh.endsWith(".vercel.app"))) return true;
        if (fwd != null && fwd.toLowerCase().contains("host=crud-cohan.vercel.app")) return true;
        if (fwd != null && fwd.toLowerCase().contains("host=") && fwd.toLowerCase().contains(".vercel.app")) return true;
        if (referer != null && referer.contains("vercel.app")) return true;
        if (origin != null && origin.contains("vercel.app")) return true;
        return server != null && (server.equals("crud-cohan.vercel.app") || server.endsWith(".vercel.app"));
    }

    private AuthenticationSuccessHandler hostAwareSuccessHandler(String frontUrl) {
        return (request, response, authentication) -> {
            // If request came through Vercel, go back to frontend URL; else stay on backend domain
            if (isVercelRequest(request)) {
                response.sendRedirect(frontUrl);
            } else {
                response.sendRedirect("/");
            }
        };
    }

    private AuthenticationFailureHandler hostAwareFailureHandler(String frontUrl) {
        return (request, response, exception) -> {
            // If request came through Vercel, keep user on Vercel login; else backend login
            if (isVercelRequest(request)) {
                // Ensure absolute redirect to the Vercel domain
                String target = frontUrl.endsWith("/") ? (frontUrl + "login?error") : (frontUrl + "/login?error");
                response.sendRedirect(target);
            } else {
                response.sendRedirect("/login?error");
            }
        };
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
            String frontUrl = effectiveFrontendUrl(frontendUrl);
            // For OAuth2, always send users back to the SPA (Vercel) on success
            AuthenticationSuccessHandler oauthSuccessHandler = (request, response, authentication) -> response.sendRedirect(frontUrl);
            // For form login, respect the origin (Vercel vs backend)
            AuthenticationSuccessHandler formSuccessHandler = hostAwareSuccessHandler(frontUrl);
            AuthenticationFailureHandler failureHandler = hostAwareFailureHandler(frontUrl);

            http
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/static/**", "/", "/index.html", "/login", "/login.html", "/oauth2/**", "/login/**", "/login/oauth2/**", "/api/auth/me", "/api/auth/debug", "/api/health/**").permitAll()
                    .anyRequest().authenticated()
                )
                // Habilitar HTTP Basic para herramientas como Postman (si se define un usuario en el entorno)
                .httpBasic(basic -> {})
                // Usar /login como página única: incluye formulario y link a GitHub
                .oauth2Login(o -> o
                    .loginPage("/login")
                    .failureHandler(failureHandler)
                    .successHandler(oauthSuccessHandler)
                )
                .formLogin(form -> form
                    .loginPage("/login")
                    .loginProcessingUrl("/perform_login")
                    .successHandler(formSuccessHandler)
                    .failureHandler(failureHandler)
                    .permitAll()
                )
                .logout(logout -> logout.logoutSuccessUrl("/login").permitAll());
        } else if (isLocal || isDocker) {
            // Local dev: protect API with form login, allow others, enable H2 and SOAP
            // Local dev: protect API with form login, allow others, enable H2 and SOAP
            // After successful login redirect to Vue dev server (frontendUrl)
            String frontUrl = effectiveFrontendUrl(frontendUrl);
            SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
            successHandler.setDefaultTargetUrl(frontUrl);
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
                .logout(logout -> logout.logoutSuccessUrl("/login").permitAll());
            // H2 console frames
            http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
        } else {
            // Fallback when no OAuth2 clients configured (e.g., Railway without secrets)
            // Protect API with form login and enable Basic for tools; keep login page at /login and process POSTs at /perform_login
            String frontUrl = effectiveFrontendUrl(frontendUrl);
            AuthenticationSuccessHandler successHandler = hostAwareSuccessHandler(frontUrl);
            AuthenticationFailureHandler failureHandler = hostAwareFailureHandler(frontUrl);

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
                    .failureHandler(failureHandler)
                    .permitAll()
                )
    .logout(logout -> logout.logoutSuccessUrl("/login").permitAll());
        }

        return http.build();
    }

    @Bean
    @ConditionalOnProperty(name = "app.security.dev-user.enabled", havingValue = "true", matchIfMissing = true)
    public UserDetailsService inMemoryUsers(
            @Value("${app.security.user.name:dev}") String username,
            @Value("${app.security.user.password:}") String password
    ) {
    // Solo crear el usuario si nombre y contraseña fueron provistos por variables de entorno.
    if (username != null && !username.isBlank() && password != null && !password.isBlank()) {
        UserDetails user = User.withUsername(username)
            .password("{noop}" + password)
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }
    // Si está habilitado pero no se configuraron credenciales, no crear ningún usuario.
    return new InMemoryUserDetailsManager();
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
