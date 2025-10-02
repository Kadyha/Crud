package com.example.crudapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Make /login render the static login.html (keeps URL /login)
        registry.addViewController("/login").setViewName("forward:/login.html");
        // Optional: also map root to index.html to be explicit
        registry.addViewController("/").setViewName("forward:/index.html");
    }
}
