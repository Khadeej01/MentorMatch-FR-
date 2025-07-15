package com.mentormatch.mapper;

import com.mentormatch.model.Apprenant;
import com.mentormatch.dto.ApprenantDTO;

public class ApprenantMapper {
    public static ApprenantDTO toDTO(Apprenant apprenant) {
        ApprenantDTO dto = new ApprenantDTO();
        dto.setId(apprenant.getId());
        dto.setNom(apprenant.getNom());
        dto.setEmail(apprenant.getEmail());
        dto.setObjectifs(apprenant.getObjectifs());
        dto.setNiveau(apprenant.getNiveau());
        return dto;
    }

    public static Apprenant toEntity(ApprenantDTO dto) {
        Apprenant apprenant = new Apprenant();
        apprenant.setId(dto.getId());
        apprenant.setNom(dto.getNom());
        apprenant.setEmail(dto.getEmail());
        apprenant.setObjectifs(dto.getObjectifs());
        apprenant.setNiveau(dto.getNiveau());
        return apprenant;
    }
} 