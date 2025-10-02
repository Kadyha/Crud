package com.example.crudapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import jakarta.annotation.PostConstruct;

@Configuration
public class StartupEnvLogger {

    private final Environment env;

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String username;

    public StartupEnvLogger(Environment env) {
        this.env = env;
    }

    @PostConstruct
    public void logAtStartup() {
        String profiles = String.join(",", env.getActiveProfiles());
        String mysqlHost = firstNonEmpty(env.getProperty("MYSQL_HOST"), env.getProperty("MYSQLHOST"));
        String mysqlPort = firstNonEmpty(env.getProperty("MYSQL_PORT"), env.getProperty("MYSQLPORT"));
        String mysqlDb = firstNonEmpty(env.getProperty("MYSQL_DATABASE"), env.getProperty("MYSQLDATABASE"));
        String sdUrl = env.getProperty("SPRING_DATASOURCE_URL");

        System.out.println("[Startup] Active profiles: " + profiles);
        System.out.println("[Startup] Resolved JDBC URL: " + sanitize(jdbcUrl));
        System.out.println("[Startup] spring.datasource.username: " + (username != null ? username : "<null>"));
        System.out.println("[Startup] Env MYSQLHOST/MYSQL_HOST: " + (mysqlHost != null ? mysqlHost : "<null>"));
        System.out.println("[Startup] Env MYSQLPORT/MYSQL_PORT: " + (mysqlPort != null ? mysqlPort : "<null>"));
        System.out.println("[Startup] Env MYSQLDATABASE/MYSQL_DATABASE: " + (mysqlDb != null ? mysqlDb : "<null>"));
        System.out.println("[Startup] SPRING_DATASOURCE_URL present: " + (sdUrl != null));
    }

    private String sanitize(String url) {
        if (url == null) return null;
        return url.replaceAll("//([^/:@]+):([^@]+)@", "//***:***@");
    }

    private String firstNonEmpty(String a, String b) {
        if (a != null && !a.isBlank()) return a; 
        if (b != null && !b.isBlank()) return b; 
        return null;
    }
}
