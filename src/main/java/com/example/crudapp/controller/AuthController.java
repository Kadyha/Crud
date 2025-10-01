package com.example.crudapp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AuthController {

    @GetMapping("/api/auth/me")
    public Map<String, Object> me(Authentication authentication) {
        Map<String, Object> body = new HashMap<>();
        if (authentication == null || !authentication.isAuthenticated()) {
            body.put("authenticated", false);
            return body;
        }
        body.put("authenticated", true);
        body.put("name", authentication.getName());
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        body.put("roles", roles);
        return body;
    }
}
