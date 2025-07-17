package com.mentormatch.controller;

import com.mentormatch.model.Utilisateur;
import com.mentormatch.repository.UtilisateurRepository;
import com.mentormatch.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public String register(@RequestBody Utilisateur utilisateur) {
        if (utilisateurRepository.findByEmail(utilisateur.getEmail()) != null) {
            return "Email déjà utilisé";
        }
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        utilisateurRepository.save(utilisateur);
        return "Inscription réussie";
    }

    @PostMapping("/login")
    public String login(@RequestBody Utilisateur utilisateur) {
        Utilisateur user = utilisateurRepository.findByEmail(utilisateur.getEmail());
        if (user == null || !passwordEncoder.matches(utilisateur.getPassword(), user.getPassword())) {
            return "Email ou mot de passe incorrect";
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return token;
    }
} 