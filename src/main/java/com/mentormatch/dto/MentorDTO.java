package com.mentormatch.dto;

public class MentorDTO {
    private Long id;
    private String nom;
    private String email;
    private String competences;
    private String experience;
    private boolean available;
    private String role;

    // Constructors
    public MentorDTO() {}

    public MentorDTO(Long id, String nom, String email, String competences, 
                    String experience, boolean available, String role) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.competences = competences;
        this.experience = experience;
        this.available = available;
        this.role = role;
    }

    // Getters and Setters
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

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}