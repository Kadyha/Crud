package com.example.crudapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    private final JdbcTemplate jdbcTemplate;

    public HealthController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/db")
    public ResponseEntity<Map<String, Object>> db() {
        Map<String, Object> body = new HashMap<>();
        try {
            Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            body.put("status", (result != null && result == 1) ? "UP" : "UNKNOWN");
            return ResponseEntity.ok(body);
        } catch (Exception ex) {
            body.put("status", "DOWN");
            body.put("error", ex.getClass().getSimpleName() + ": " + ex.getMessage());
            return ResponseEntity.status(503).body(body);
        }
    }
}
