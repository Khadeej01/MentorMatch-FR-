package com.mentormatch.controller;

import com.mentormatch.dto.MentorDTO;
import com.mentormatch.service.MentorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@Tag(name = "Test", description = "API de test pour vérifier le fonctionnement")
public class TestController {
    
    private final MentorService mentorService;
    private final DataSource dataSource;
    
    public TestController(MentorService mentorService, DataSource dataSource) {
        this.mentorService = mentorService;
        this.dataSource = dataSource;
    }

    @Operation(summary = "Test de l'API", description = "Endpoint simple pour vérifier que l'API fonctionne")
    @GetMapping("/ping")
    public Map<String, String> ping() {
        return Map.of(
            "message", "API MentorMatch fonctionne !",
            "status", "OK",
            "timestamp", java.time.LocalDateTime.now().toString()
        );
    }

    @Operation(summary = "Test Swagger", description = "Endpoint pour vérifier que Swagger fonctionne")
    @GetMapping("/swagger")
    public Map<String, String> swagger() {
        return Map.of(
            "swagger_ui", "http://localhost:8080/swagger-ui.html",
            "api_docs", "http://localhost:8080/v3/api-docs",
            "status", "Swagger configuré"
        );
    }

    @Operation(summary = "Test Admin", description = "Endpoint pour vérifier si l'admin existe")
    @GetMapping("/admin")
    public Map<String, String> testAdmin() {
        return Map.of(
            "admin_init", "http://localhost:8080/api/admin/init",
            "admin_login", "http://localhost:8080/api/admin/login",
            "message", "Utilisez d'abord /api/admin/init pour créer l'admin"
        );
    }

    @Operation(summary = "Initialiser des mentors de test", description = "Crée des mentors de test pour les tests Swagger")
    @PostMapping("/mentors/init")
    public ResponseEntity<Map<String, Object>> initTestMentors() {
        try {
            // Créer des mentors de test
            List<MentorDTO> testMentors = List.of(
                new MentorDTO(null, "Jean Dupont", "jean.dupont@example.com", 
                    "Java, Spring Boot, Microservices", "5 ans d'expérience en développement", true, "MENTOR"),
                new MentorDTO(null, "Marie Martin", "marie.martin@example.com", 
                    "React, Angular, TypeScript", "3 ans d'expérience en frontend", true, "MENTOR"),
                new MentorDTO(null, "Pierre Durand", "pierre.durand@example.com", 
                    "Python, Data Science, Machine Learning", "7 ans d'expérience en IA", false, "MENTOR"),
                new MentorDTO(null, "Sophie Bernard", "sophie.bernard@example.com", 
                    "DevOps, Docker, Kubernetes", "4 ans d'expérience en infrastructure", true, "MENTOR"),
                new MentorDTO(null, "Lucas Moreau", "lucas.moreau@example.com", 
                    "Node.js, Express, MongoDB", "2 ans d'expérience en backend", true, "MENTOR")
            );

            // Sauvegarder les mentors
            List<MentorDTO> savedMentors = testMentors.stream()
                .map(mentorService::save)
                .toList();

            return ResponseEntity.ok(Map.of(
                "message", "Mentors de test créés avec succès",
                "count", savedMentors.size(),
                "mentors", savedMentors
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Erreur lors de la création des mentors de test",
                "message", e.getMessage()
            ));
        }
    }

    @Operation(summary = "Lister tous les mentors", description = "Récupère tous les mentors pour les tests")
    @GetMapping("/mentors")
    public ResponseEntity<List<MentorDTO>> getAllTestMentors() {
        List<MentorDTO> mentors = mentorService.findAll();
        return ResponseEntity.ok(mentors);
    }

    @Operation(summary = "Vérifier la connexion DB", description = "Vérifie la connexion à la base de données via un simple SELECT 1")
    @GetMapping("/db")
    public ResponseEntity<Map<String, Object>> checkDatabaseConnection() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT 1")) {
            boolean ok = rs.next() && rs.getInt(1) == 1;
            return ResponseEntity.ok(Map.of(
                "status", ok ? "UP" : "UNKNOWN",
                "database", conn.getMetaData().getURL(),
                "driver", conn.getMetaData().getDriverName()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "status", "DOWN",
                "error", e.getClass().getSimpleName(),
                "message", e.getMessage()
            ));
        }
    }
}
