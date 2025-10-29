/**
 * ---------------------------------------------------------------
 * File Name   : CorsConfig.java
 * Package     : com.neb.config
 * ---------------------------------------------------------------
 * Purpose :
 *   This class allows the frontend and backend to communicate
 *   without getting blocked by CORS (Cross-Origin Resource Sharing)
 *   errors in the browser.
 *
 * What it Does :
 *    Allows the frontend running on "http://localhost:5173"
 *     to access backend APIs that start with "/api/**"
 *    Permits different HTTP methods like GET, POST, PUT, DELETE
 *    Accepts all headers from the frontend
 *    Allows cookies and login credentials to be shared
 *
 * Why it is Needed :
 *   When the frontend and backend run on different ports,
 *   browsers block API calls for security reasons.
 *   This configuration removes that restriction.
 *
 * Example :
 *   Frontend: http://localhost:5173
 *   Backend : http://localhost:8080
 *   → This setup allows both to work together smoothly.
 *
 * Result :
 *   Prevents “CORS policy error” and allows data exchange
 *   between fronted and backend during development.
 * ---------------------------------------------------------------
 */

package com.neb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                // Allow frontend (localhost:5173) to access backend APIs
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:5173")  // frontend URL
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // allowed request types
                        .allowedHeaders("*")  // all headers allowed
                        .allowCredentials(true); // allow cookies / tokens
            }
        };
    }
}
