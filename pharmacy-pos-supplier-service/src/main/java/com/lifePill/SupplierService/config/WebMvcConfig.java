package com.lifePill.SupplierService.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for Web MVC.
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * Configures CORS mappings.
     *
     * @param registry CorsRegistry object
     */

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173","http://localhost:3000")
                .allowedMethods("*")
                .allowedHeaders("Content-Type", "Authorization")
                .allowCredentials(true);
    }
}
