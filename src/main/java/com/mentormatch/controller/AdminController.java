package com.mentormatch.controller;

import com.mentormatch.model.Admin;
import com.mentormatch.service.AdminService;
import com.mentormatch.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String email = credentials.get("email");
        String password = credentials.get("password");
        
        // Accepter soit username soit email
        String loginIdentifier = (username != null) ? username : email;
        
        Admin admin = adminService.authenticate(loginIdentifier, password);
        if (admin != null) {
            String token = jwtUtil.generateToken(admin.getEmail(), admin.getRole());
            return ResponseEntity.ok(Map.of(
                "token", token,
                "user", Map.of(
                    "id", admin.getId(),
                    "username", admin.getUsername(),
                    "email", admin.getEmail(),
                    "role", admin.getRole()
                )
            ));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid credentials"));
        }
    }
    
    @GetMapping("/dashboard/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = adminService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }
    
    @PostMapping("/init")
    public ResponseEntity<?> initializeAdmin() {
        Admin admin = adminService.createDefaultAdmin();
        if (admin != null) {
            return ResponseEntity.ok(Map.of("message", "Admin created successfully", "username", "admin", "password", "admin123"));
        } else {
            return ResponseEntity.ok(Map.of("message", "Admin already exists"));
        }
    }
}