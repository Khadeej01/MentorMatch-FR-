package com.mentormatch.mapper;

import com.mentormatch.model.Mentor;
import com.mentormatch.dto.MentorDTO;

public class MentorMapper {
    public static MentorDTO toDTO(Mentor mentor) {
        MentorDTO dto = new MentorDTO();
        dto.setId(mentor.getId());
        dto.setNom(mentor.getNom());
        dto.setEmail(mentor.getEmail());
        dto.setCompetences(mentor.getCompetences());
        dto.setExperience(mentor.getExperience());
        dto.setAvailable(mentor.isAvailable());
        return dto;
    }

    public static Mentor toEntity(MentorDTO dto) {
        Mentor mentor = new Mentor();
        mentor.setId(dto.getId());
        mentor.setNom(dto.getNom());
        mentor.setEmail(dto.getEmail());
        mentor.setCompetences(dto.getCompetences());
        mentor.setExperience(dto.getExperience());
        mentor.setAvailable(dto.isAvailable());
        return mentor;
    }
} 