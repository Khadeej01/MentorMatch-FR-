package com.mentormatch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;

@Entity
public class Apprenant extends Utilisateur {
    private String objectifs;
    private String niveau;
    
    @Column(nullable = false)
    private Boolean active = true;  // Default value set to true

    public String getObjectifs() { return objectifs; }
    public void setObjectifs(String objectifs) { this.objectifs = objectifs; }

    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }
    
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
} 