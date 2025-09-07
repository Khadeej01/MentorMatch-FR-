package com.mentormatch.service;

import com.mentormatch.model.Admin;
import com.mentormatch.repository.AdminRepository;
import com.mentormatch.repository.MentorRepository;
import com.mentormatch.repository.ApprenantRepository;
import com.mentormatch.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AdminService {
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private MentorRepository mentorRepository;
    
    @Autowired
    private ApprenantRepository apprenantRepository;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public Admin authenticate(String loginIdentifier, String password) {
        // Essayer d'abord par username
        Optional<Admin> adminOpt = adminRepository.findByUsername(loginIdentifier);
        
        // Si pas trouvé par username, essayer par email
        if (adminOpt.isEmpty()) {
            adminOpt = adminRepository.findByEmail(loginIdentifier);
        }
        
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            if (passwordEncoder.matches(password, admin.getPassword())) {
                return admin;
            }
        }
        return null;
    }
    
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        // Compter les utilisateurs
        long totalMentors = mentorRepository.count();
        long totalApprenants = apprenantRepository.count();
        
        // Compter les réservations
        long totalBookings = bookingRepository.count();
        long pendingBookings = bookingRepository.findByStatusOrderByDateTimeDesc("PENDING").size();
        long confirmedBookings = bookingRepository.findByStatusOrderByDateTimeDesc("CONFIRMED").size();
        long completedBookings = bookingRepository.findByStatusOrderByDateTimeDesc("COMPLETED").size();
        
        stats.put("totalMentors", totalMentors);
        stats.put("totalApprenants", totalApprenants);
        stats.put("totalUsers", totalMentors + totalApprenants);
        stats.put("totalBookings", totalBookings);
        stats.put("pendingBookings", pendingBookings);
        stats.put("confirmedBookings", confirmedBookings);
        stats.put("completedBookings", completedBookings);
        
        return stats;
    }
    
    public Admin createDefaultAdmin() {
        // Vérifier si un admin existe déjà
        if (adminRepository.count() > 0) {
            return null; // Admin déjà créé
        }
        
        // Créer l'admin par défaut
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setEmail("admin@mentormatch.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole("ADMIN");
        
        return adminRepository.save(admin);
    }
}