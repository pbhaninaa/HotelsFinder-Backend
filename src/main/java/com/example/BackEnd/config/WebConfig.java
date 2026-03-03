package com.example.BackEnd.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.cors.allowed-origins:}")
    private String allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] origins = allowedOrigins != null && !allowedOrigins.isBlank()
                ? allowedOrigins.split(",\\s*")
                : new String[]{
                        "http://localhost:3000",
                        "http://localhost:4200",
                        "http://localhost:4201",
                        "https://*.vercel.app"
                };
        registry.addMapping("/**")
                .allowedOriginPatterns(origins)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
