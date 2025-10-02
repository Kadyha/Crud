package com.example.crudapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthDebugController {

    @Value("${spring.security.oauth2.client.registration.github.client-id:}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.github.redirect-uri:}")
    private String redirectTemplate;

    @Value("${app.frontend.url:http://localhost:5173}")
    private String frontendUrl;

    @GetMapping("/debug")
    public Map<String, Object> debug(HttpServletRequest request) {
        Map<String, Object> m = new HashMap<>();
        m.put("clientId", clientId);
        m.put("redirectUriTemplate", redirectTemplate);
        m.put("frontendUrl", frontendUrl);

        // Compute effective base URL from forwarded headers or request
        String forwardedProto = header(request, "X-Forwarded-Proto");
        String forwardedHost = header(request, "X-Forwarded-Host");
        String scheme = forwardedProto != null ? forwardedProto : request.getScheme();
        String host = forwardedHost != null ? forwardedHost : request.getServerName();

        String baseUrl = UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .build()
                .toUriString();
        m.put("detectedBaseUrl", baseUrl);

        if (redirectTemplate != null && redirectTemplate.contains("{baseUrl}")) {
            String resolved = redirectTemplate.replace("{baseUrl}", baseUrl);
            m.put("resolvedRedirectUri", resolved);
        }
        return m;
    }

    private static String header(HttpServletRequest req, String name) {
        String v = req.getHeader(name);
        return (v == null || v.isBlank()) ? null : v;
    }
}
