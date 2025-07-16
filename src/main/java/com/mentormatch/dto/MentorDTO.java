package com.mentormatch.dto;

public class MentorDTO {
    private Long id;
    private String nom;
    private String email;
    private String competences;
    private String experience;
    private boolean isAvailable;
    private String role;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCompetences() { return competences; }
    public void setCompetences(String competences) { this.competences = competences; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean isAvailable) { this.isAvailable = isAvailable; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
} 