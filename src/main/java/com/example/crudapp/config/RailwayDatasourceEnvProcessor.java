package com.example.crudapp.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * On Railway, prefer internal host MYSQLHOST when available. If not available, or when APP_DB_USE_PUBLIC_URL=true,
 * auto-derive JDBC properties from MYSQL_PUBLIC_URL (format: mysql://user:pass@host:port/db).
 * This runs very early so Spring Boot datasource auto-config picks up the overrides.
 */
public class RailwayDatasourceEnvProcessor implements EnvironmentPostProcessor, Ordered {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String explicitJdbc = firstNonEmpty(
                environment.getProperty("SPRING_DATASOURCE_URL"),
                environment.getProperty("spring.datasource.url")
        );
        if (StringUtils.hasText(explicitJdbc)) {
            return; // respect explicit JDBC
        }

        boolean hasInternal = hasTextAny(
                environment.getProperty("MYSQL_HOST"),
                environment.getProperty("MYSQLHOST")
        );
        boolean forcePublic = Boolean.parseBoolean(environment.getProperty("APP_DB_USE_PUBLIC_URL", "false"));
        String publicUrl = environment.getProperty("MYSQL_PUBLIC_URL");

        if ((!hasInternal || forcePublic) && StringUtils.hasText(publicUrl)) {
            try {
                URI uri = URI.create(publicUrl);
                String userInfo = uri.getUserInfo(); // user:pass
                String user = null;
                String pass = null;
                if (userInfo != null) {
                    int idx = userInfo.indexOf(':');
                    if (idx > -1) {
                        user = userInfo.substring(0, idx);
                        pass = userInfo.substring(idx + 1);
                    } else {
                        user = userInfo;
                    }
                }
                String host = uri.getHost();
                int port = uri.getPort();
                String path = uri.getPath(); // /dbname
                String db = (path != null && path.startsWith("/")) ? path.substring(1) : path;
                if (!StringUtils.hasText(host) || port <= 0 || !StringUtils.hasText(db) || !StringUtils.hasText(user)) {
                    System.out.println("[RailwayDS] MYSQL_PUBLIC_URL missing pieces; skipping fallback");
                    return;
                }
                String jdbc = String.format(
                        "jdbc:mysql://%s:%d/%s?useSSL=true&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                        host, port, db
                );

                Map<String, Object> props = new HashMap<>();
                props.put("spring.datasource.url", jdbc);
                props.put("spring.datasource.username", user);
                if (StringUtils.hasText(pass)) {
                    props.put("spring.datasource.password", pass);
                }

                environment.getPropertySources().addFirst(new MapPropertySource(
                        "railway-public-ds-override", props
                ));
                System.out.println("[RailwayDS] Applied public JDBC fallback based on MYSQL_PUBLIC_URL (host=" + host + ":" + port + ", db=" + db + ")");
            } catch (Exception ex) {
                System.out.println("[RailwayDS] Failed to parse MYSQL_PUBLIC_URL: " + ex.getMessage());
            }
        }
    }

    private boolean hasTextAny(String... values) {
        for (String v : values) {
            if (StringUtils.hasText(v)) return true;
        }
        return false;
    }

    private String firstNonEmpty(String... values) {
        for (String v : values) {
            if (StringUtils.hasText(v)) return v;
        }
        return null;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
