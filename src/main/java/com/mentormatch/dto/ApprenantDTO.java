package com.mentormatch.dto;

public class ApprenantDTO {
    private Long id;
    private String nom;
    private String email;
    private String objectifs;
    private String niveau;
    private String role;

    // Constructors
    public ApprenantDTO() {}

    public ApprenantDTO(Long id, String nom, String email, String objectifs, 
                       String niveau, String role) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.objectifs = objectifs;
        this.niveau = niveau;
        this.role = role;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getObjectifs() { return objectifs; }
    public void setObjectifs(String objectifs) { this.objectifs = objectifs; }

    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}