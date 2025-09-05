package com.mentormatch.dto;

import java.time.LocalDateTime;

public class SessionDTO {
    private Long id;
    private LocalDateTime dateHeure;
    private String sujet;
    private Long mentorId;
    private Long apprenantId;

    // Constructors
    public SessionDTO() {}

    public SessionDTO(Long id, LocalDateTime dateHeure, String sujet, 
                     Long mentorId, Long apprenantId) {
        this.id = id;
        this.dateHeure = dateHeure;
        this.sujet = sujet;
        this.mentorId = mentorId;
        this.apprenantId = apprenantId;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDateHeure() { return dateHeure; }
    public void setDateHeure(LocalDateTime dateHeure) { this.dateHeure = dateHeure; }

    public String getSujet() { return sujet; }
    public void setSujet(String sujet) { this.sujet = sujet; }

    public Long getMentorId() { return mentorId; }
    public void setMentorId(Long mentorId) { this.mentorId = mentorId; }

    public Long getApprenantId() { return apprenantId; }
    public void setApprenantId(Long apprenantId) { this.apprenantId = apprenantId; }
}