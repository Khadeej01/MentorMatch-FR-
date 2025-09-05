package com.mentormatch.security;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    void testGenerateToken() {
        // Given
        String email = "test@example.com";
        String role = "MENTOR";

        // When
        String token = jwtUtil.generateToken(email, role);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains(".")); // JWT should contain dots
    }

    @Test
    void testExtractEmail() {
        // Given
        String email = "test@example.com";
        String role = "MENTOR";
        String token = jwtUtil.generateToken(email, role);

        // When
        String extractedEmail = jwtUtil.extractEmail(token);

        // Then
        assertEquals(email, extractedEmail);
    }

    @Test
    void testExtractRole() {
        // Given
        String email = "test@example.com";
        String role = "ADMIN";
        String token = jwtUtil.generateToken(email, role);

        // When
        String extractedRole = jwtUtil.extractRole(token);

        // Then
        assertEquals(role, extractedRole);
    }

    @Test
    void testExtractClaims() {
        // Given
        String email = "test@example.com";
        String role = "APPRENANT";
        String token = jwtUtil.generateToken(email, role);

        // When
        Claims claims = jwtUtil.extractClaims(token);

        // Then
        assertNotNull(claims);
        assertEquals(email, claims.getSubject());
        assertEquals(role, claims.get("role"));
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
    }

    @Test
    void testTokenExpiration() {
        // Given
        String email = "test@example.com";
        String role = "MENTOR";

        // When
        String token = jwtUtil.generateToken(email, role);
        Claims claims = jwtUtil.extractClaims(token);

        // Then
        assertNotNull(claims.getExpiration());
        assertTrue(claims.getExpiration().getTime() > System.currentTimeMillis());
    }

    @Test
    void testTokenWithDifferentRoles() {
        // Given
        String email = "admin@example.com";
        String[] roles = {"ADMIN", "MENTOR", "APPRENANT"};

        // When & Then
        for (String role : roles) {
            String token = jwtUtil.generateToken(email, role);
            String extractedRole = jwtUtil.extractRole(token);
            assertEquals(role, extractedRole);
        }
    }

    @Test
    void testTokenWithDifferentEmails() {
        // Given
        String role = "MENTOR";
        String[] emails = {"user1@example.com", "user2@example.com", "admin@test.com"};

        // When & Then
        for (String email : emails) {
            String token = jwtUtil.generateToken(email, role);
            String extractedEmail = jwtUtil.extractEmail(token);
            assertEquals(email, extractedEmail);
        }
    }

    @Test
    void testInvalidToken() {
        // Given
        String invalidToken = "invalid.token.here";

        // When & Then
        assertThrows(Exception.class, () -> jwtUtil.extractClaims(invalidToken));
    }

    @Test
    void testEmptyToken() {
        // Given
        String emptyToken = "";

        // When & Then
        assertThrows(Exception.class, () -> jwtUtil.extractClaims(emptyToken));
    }

    @Test
    void testNullToken() {
        // Given
        String nullToken = null;

        // When & Then
        assertThrows(Exception.class, () -> jwtUtil.extractClaims(nullToken));
    }

    @Test
    void testTokenConsistency() {
        // Given
        String email = "consistency@test.com";
        String role = "MENTOR";

        // When
        String token1 = jwtUtil.generateToken(email, role);
        String token2 = jwtUtil.generateToken(email, role);

        // Then
        // Tokens should be different due to different issued times
        assertNotEquals(token1, token2);
        
        // But should extract same information
        assertEquals(jwtUtil.extractEmail(token1), jwtUtil.extractEmail(token2));
        assertEquals(jwtUtil.extractRole(token1), jwtUtil.extractRole(token2));
    }
}
