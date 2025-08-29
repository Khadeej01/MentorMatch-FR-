package com.mentormatch.mapper;

import com.mentormatch.model.Apprenant;
import com.mentormatch.dto.ApprenantDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApprenantMapperTest {

    @Test
    public void testToDTO() {
        // Given
        Apprenant apprenant = new Apprenant();
        apprenant.setId(1L);
        apprenant.setNom("Test User");
        apprenant.setEmail("test@example.com");
        apprenant.setObjectifs("Learn Java");
        apprenant.setNiveau("Beginner");
        apprenant.setRole("APPRENANT");
        apprenant.setPassword("password"); // This field should not be mapped

        // When
        ApprenantDTO apprenantDTO = ApprenantMapper.toDTO(apprenant);

        // Then
        assertEquals(apprenant.getId(), apprenantDTO.getId());
        assertEquals(apprenant.getNom(), apprenantDTO.getNom());
        assertEquals(apprenant.getEmail(), apprenantDTO.getEmail());
        assertEquals(apprenant.getObjectifs(), apprenantDTO.getObjectifs());
        assertEquals(apprenant.getNiveau(), apprenantDTO.getNiveau());
        assertEquals(apprenant.getRole(), apprenantDTO.getRole());
    }

    @Test
    public void testToEntity() {
        // Given
        ApprenantDTO apprenantDTO = new ApprenantDTO();
        apprenantDTO.setId(1L);
        apprenantDTO.setNom("Test User");
        apprenantDTO.setEmail("test@example.com");
        apprenantDTO.setObjectifs("Learn Java");
        apprenantDTO.setNiveau("Beginner");
        apprenantDTO.setRole("APPRENANT");

        // When
        Apprenant apprenant = ApprenantMapper.toEntity(apprenantDTO);

        // Then
        assertEquals(apprenantDTO.getId(), apprenant.getId());
        assertEquals(apprenantDTO.getNom(), apprenant.getNom());
        assertEquals(apprenantDTO.getEmail(), apprenant.getEmail());
        assertEquals(apprenantDTO.getObjectifs(), apprenant.getObjectifs());
        assertEquals(apprenantDTO.getNiveau(), apprenant.getNiveau());
        assertEquals(apprenantDTO.getRole(), apprenant.getRole());
    }
}
