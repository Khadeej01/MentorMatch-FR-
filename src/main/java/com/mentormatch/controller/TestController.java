package com.mentormatch.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
@Tag(name = "Test", description = "API de test pour vérifier le fonctionnement")
public class TestController {

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
}
