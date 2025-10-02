package com.example.crudapp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/debug/db")
public class DbDebugController {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @GetMapping
    public Map<String, Object> info() {
        Map<String, Object> out = new HashMap<>();
        out.put("jdbcUrl", sanitize(jdbcUrl));
        out.put("username", username);
        return out;
    }

    private String sanitize(String url) {
        if (url == null) return null;
        // Remove credentials if present in URL (shouldn't be, but just in case)
        // jdbc:mysql://user:pass@host:port/db -> jdbc:mysql://***:***@host:port/db
        return url.replaceAll("//([^/:@]+):([^@]+)@", "//***:***@");
    }
}
