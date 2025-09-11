package com.mentormatch.controller;

import com.mentormatch.model.Admin;
import com.mentormatch.model.Apprenant;
import com.mentormatch.model.Mentor;
import com.mentormatch.repository.AdminRepository;
import com.mentormatch.repository.ApprenantRepository;
import com.mentormatch.repository.MentorRepository;
import com.mentormatch.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Authentification", description = "API pour l'authentification des utilisateurs")
public class AuthController {
    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private ApprenantRepository apprenantRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Operation(summary = "Inscription utilisateur", description = "Inscrit un nouveau mentor ou apprenant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscription réussie"),
            @ApiResponse(responseCode = "400", description = "Email déjà utilisé ou données invalides")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> userData) {
        String email = userData.get("email");
        String password = userData.get("password");
        String role = userData.get("role");
        String nom = userData.get("nom");

        if (isEmailExists(email)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email déjà utilisé"));
        }

        Object savedUser;
        String dbRole;

        if ("mentor".equalsIgnoreCase(role)) {
            Mentor mentor = new Mentor();
            mentor.setNom(nom);
            mentor.setEmail(email);
            mentor.setPassword(passwordEncoder.encode(password));
            mentor.setRole("MENTOR");
            mentor.setCompetences(userData.getOrDefault("competences", ""));
            mentor.setExperience(userData.getOrDefault("experience", ""));
            mentor.setAvailable(true);
            savedUser = mentorRepository.save(mentor);
            dbRole = "MENTOR";
        } else if ("learner".equalsIgnoreCase(role)) {
            Apprenant apprenant = new Apprenant();
            apprenant.setNom(nom);
            apprenant.setEmail(email);
            apprenant.setPassword(passwordEncoder.encode(password));
            apprenant.setRole("APPRENANT");
            savedUser = apprenantRepository.save(apprenant);
            dbRole = "APPRENANT";
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Rôle invalide"));
        }

        String token = jwtUtil.generateToken(email, dbRole);
        String frontendRole = "APPRENANT".equalsIgnoreCase(dbRole) ? "learner" : dbRole.toLowerCase();

        return ResponseEntity.ok(Map.of(
            "token", token,
            "user", Map.of(
                "id", getUserId(savedUser),
                "nom", getUserNom(savedUser),
                "email", email,
                "role", frontendRole
            )
        ));
    }

    @Operation(summary = "Connexion utilisateur", description = "Connecte un mentor ou apprenant et retourne un token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Connexion réussie"),
            @ApiResponse(responseCode = "400", description = "Email ou mot de passe incorrect")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        // Chercher dans tous les types d'utilisateurs
        Object user = findUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email ou mot de passe incorrect"));
        }

        String userPassword = getUserPassword(user);
        if (!passwordEncoder.matches(password, userPassword)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email ou mot de passe incorrect"));
        }

        String role = getUserRole(user);
        String nom = getUserNom(user);
        Long id = getUserId(user);

        String token = jwtUtil.generateToken(email, role);
        return ResponseEntity.ok(Map.of(
            "token", token,
            "user", Map.of(
                "id", id,
                "nom", nom,
                "email", email,
                "role", role
            )
        ));
    }

    private boolean isEmailExists(String email) {
        return mentorRepository.findByEmail(email).isPresent() ||
               apprenantRepository.findByEmail(email).isPresent() ||
               adminRepository.findByEmail(email).isPresent();
    }

    private Object findUserByEmail(String email) {
        Optional<Mentor> mentor = mentorRepository.findByEmail(email);
        if (mentor.isPresent()) return mentor.get();

        Optional<Apprenant> apprenant = apprenantRepository.findByEmail(email);
        if (apprenant.isPresent()) return apprenant.get();

        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isPresent()) return admin.get();

        return null;
    }

    private String getUserPassword(Object user) {
        if (user instanceof Mentor) return ((Mentor) user).getPassword();
        if (user instanceof Apprenant) return ((Apprenant) user).getPassword();
        if (user instanceof Admin) return ((Admin) user).getPassword();
        return null;
    }

    private String getUserRole(Object user) {
        if (user instanceof Mentor) return ((Mentor) user).getRole();
        if (user instanceof Apprenant) return ((Apprenant) user).getRole();
        if (user instanceof Admin) return ((Admin) user).getRole();
        return null;
    }

    private String getUserNom(Object user) {
        if (user instanceof Mentor) return ((Mentor) user).getNom();
        if (user instanceof Apprenant) return ((Apprenant) user).getNom();
        if (user instanceof Admin) return ((Admin) user).getUsername();
        return null;
    }

    private Long getUserId(Object user) {
        if (user instanceof Mentor) return ((Mentor) user).getId();
        if (user instanceof Apprenant) return ((Apprenant) user).getId();
        if (user instanceof Admin) return ((Admin) user).getId();
        return null;
    }
} 