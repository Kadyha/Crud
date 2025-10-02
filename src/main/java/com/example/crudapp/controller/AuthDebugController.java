package com.example.crudapp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Map<String, Object> debug() {
        Map<String, Object> m = new HashMap<>();
        m.put("clientId", clientId);
        m.put("redirectUriTemplate", redirectTemplate);
        m.put("frontendUrl", frontendUrl);
        return m;
    }
}
