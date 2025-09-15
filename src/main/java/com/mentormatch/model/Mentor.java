package com.mentormatch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;

@Entity
public class Mentor extends Utilisateur {
    private String competences;
    private String experience;
    private boolean isAvailable;
    
    @Column(nullable = false)
    private Boolean active = true;  // Default value set to true
    
    @Column(nullable = false)
    private String status = "PENDING";  // Default status for new mentors

    public String getCompetences() { return competences; }
    public void setCompetences(String competences) { this.competences = competences; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean isAvailable) { this.isAvailable = isAvailable; }
    
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}