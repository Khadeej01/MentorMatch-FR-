package com.mentormatch.model;

import jakarta.persistence.Entity;

@Entity
public class Apprenant extends Utilisateur {
    private String objectifs;
    private String niveau;

    public String getObjectifs() { return objectifs; }
    public void setObjectifs(String objectifs) { this.objectifs = objectifs; }

    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }
} 