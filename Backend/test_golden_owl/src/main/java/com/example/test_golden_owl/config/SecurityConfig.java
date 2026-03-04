package com.example.test_golden_owl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
@Configuration

public class SecurityConfig {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:5173");
        corsConfiguration.addAllowedOrigin("http://127.0.0.1:5500");
        corsConfiguration.addAllowedOrigin("https://smart-restaurant-silk.vercel.app");
        corsConfiguration.addAllowedMethod("*"); // cho phép tất cả các method (GET, POST, PUT, DELETE, ...)
        corsConfiguration.addAllowedHeader("*"); // cho phép tất cả các header
        corsConfiguration.setAllowCredentials(true); // cho phép gửi cookie

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource =
                new UrlBasedCorsConfigurationSource(); // ánh xạ cấu hình CORS cho các endpoint
        urlBasedCorsConfigurationSource.registerCorsConfiguration(
                "/**", corsConfiguration); // áp dụng cấu hình CORS cho tất cả các endpoint

        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
