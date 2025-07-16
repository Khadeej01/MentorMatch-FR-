package com.mentormatch.model;

import jakarta.persistence.Entity;

@Entity
public class Mentor extends Utilisateur {
    private String competences;
    private String experience;

    public String getCompetences() { return competences; }
    public void setCompetences(String competences) { this.competences = competences; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }
}