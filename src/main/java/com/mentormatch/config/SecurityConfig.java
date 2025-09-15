package com.mentormatch.config;

import com.mentormatch.security.JwtAuthenticationFilter;
import com.mentormatch.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtil);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints - no authentication required
                        .requestMatchers(HttpMethod.POST, "/api/auth/signin", "/api/auth/signup", "/api/auth/register", "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/mentors", "/api/mentors/**").permitAll() // Public mentor viewing
                        .requestMatchers(HttpMethod.GET, "/actuator/health").permitAll() // Health check for Docker
                        .requestMatchers(HttpMethod.GET, "/api/test/**").permitAll() // Test endpoints
                        
                        // Admin endpoints - specific access control
                        .requestMatchers(HttpMethod.POST, "/api/admin/login", "/api/admin/init").permitAll() // Public admin auth
                        .requestMatchers("/api/admin/**").hasRole("ADMIN") // All other admin endpoints require ADMIN role
                        
                        // Mentor endpoints - require mentor role or admin
                        .requestMatchers(HttpMethod.PUT, "/api/mentors/**").hasAnyRole("MENTOR", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/mentors/**").hasAnyRole("MENTOR", "ADMIN")
                        
                        // Learner endpoints - require learner role or admin
                        .requestMatchers(HttpMethod.PUT, "/api/learners/**").hasAnyRole("LEARNER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/learners/**").hasAnyRole("LEARNER", "ADMIN")
                        
                        // Booking endpoints - require authentication
                        .requestMatchers("/api/bookings/**").authenticated()
                        
                        // All other endpoints require authentication
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow specific origins in production, localhost for development
        configuration.setAllowedOriginPatterns(Arrays.asList(
            "http://localhost:4200", 
            "http://localhost:3000",
            "http://127.0.0.1:4200"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L); // Cache preflight response for 1 hour
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}