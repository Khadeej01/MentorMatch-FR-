package com.mentormatch.config;

import com.mentormatch.security.JwtAuthenticationFilter;
import com.mentormatch.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                // Swagger/OpenAPI endpoints
                .requestMatchers("/swagger-ui/**", "/swagger-ui.html").permitAll()
                .requestMatchers("/v3/api-docs/**", "/v3/api-docs").permitAll()
                .requestMatchers("/swagger-resources/**", "/webjars/**").permitAll()
                
                // Test endpoints
                .requestMatchers("/api/test/**").permitAll()
                
                // Endpoints publics
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/admin/init").permitAll()
                .requestMatchers("/api/admin/login").permitAll()
                .requestMatchers("/api/mentors").permitAll()
                .requestMatchers("/api/mentors/search").permitAll()
                .requestMatchers("/api/mentors/competences/**").permitAll()
                .requestMatchers("/api/mentors/{id}").permitAll()
                
                // Endpoints admin (sauf init et login)
                .requestMatchers("/api/admin/dashboard/**").hasRole("ADMIN")
                .requestMatchers("/api/admin/stats/**").hasRole("ADMIN")
                
                // Endpoints mentors
                .requestMatchers("/api/mentors/**").hasAnyRole("MENTOR", "ADMIN")
                
                // Endpoints apprenants
                .requestMatchers("/api/apprenants/**").hasAnyRole("APPRENANT", "ADMIN")
                
                // Endpoints bookings
                .requestMatchers("/api/bookings/**").hasAnyRole("MENTOR", "APPRENANT", "ADMIN")
                
                // Endpoints sessions
                .requestMatchers("/api/sessions/**").hasAnyRole("MENTOR", "APPRENANT", "ADMIN")
                
                // Tous les autres endpoints nécessitent une authentification
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}