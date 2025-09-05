package com.mentormatch.mapper;

import com.mentormatch.dto.MentorDTO;
import com.mentormatch.model.Mentor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MentorMapperTest {

    @Test
    void testToDTO() {
        // Given
        Mentor mentor = new Mentor();
        mentor.setId(1L);
        mentor.setNom("John Doe");
        mentor.setEmail("john@example.com");
        mentor.setCompetences("Java, Spring");
        mentor.setExperience("5 years");
        mentor.setAvailable(true);
        mentor.setRole("MENTOR");

        // When
        MentorDTO dto = MentorMapper.toDTO(mentor);

        // Then
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("John Doe", dto.getNom());
        assertEquals("john@example.com", dto.getEmail());
        assertEquals("Java, Spring", dto.getCompetences());
        assertEquals("5 years", dto.getExperience());
        assertTrue(dto.isAvailable());
        assertEquals("MENTOR", dto.getRole());
    }

    @Test
    void testToDTOWithNull() {
        // When
        MentorDTO dto = MentorMapper.toDTO(null);

        // Then
        assertNull(dto);
    }

    @Test
    void testToEntity() {
        // Given
        MentorDTO dto = new MentorDTO();
        dto.setId(1L);
        dto.setNom("John Doe");
        dto.setEmail("john@example.com");
        dto.setCompetences("Java, Spring");
        dto.setExperience("5 years");
        dto.setAvailable(true);
        dto.setRole("MENTOR");

        // When
        Mentor mentor = MentorMapper.toEntity(dto);

        // Then
        assertNotNull(mentor);
        assertEquals(1L, mentor.getId());
        assertEquals("John Doe", mentor.getNom());
        assertEquals("john@example.com", mentor.getEmail());
        assertEquals("Java, Spring", mentor.getCompetences());
        assertEquals("5 years", mentor.getExperience());
        assertTrue(mentor.isAvailable());
        assertEquals("MENTOR", mentor.getRole());
    }

    @Test
    void testToEntityWithNull() {
        // When
        Mentor mentor = MentorMapper.toEntity(null);

        // Then
        assertNull(mentor);
    }

    @Test
    void testRoundTripConversion() {
        // Given
        Mentor originalMentor = new Mentor();
        originalMentor.setId(1L);
        originalMentor.setNom("John Doe");
        originalMentor.setEmail("john@example.com");
        originalMentor.setCompetences("Java, Spring");
        originalMentor.setExperience("5 years");
        originalMentor.setAvailable(true);
        originalMentor.setRole("MENTOR");

        // When
        MentorDTO dto = MentorMapper.toDTO(originalMentor);
        Mentor convertedMentor = MentorMapper.toEntity(dto);

        // Then
        assertNotNull(convertedMentor);
        assertEquals(originalMentor.getId(), convertedMentor.getId());
        assertEquals(originalMentor.getNom(), convertedMentor.getNom());
        assertEquals(originalMentor.getEmail(), convertedMentor.getEmail());
        assertEquals(originalMentor.getCompetences(), convertedMentor.getCompetences());
        assertEquals(originalMentor.getExperience(), convertedMentor.getExperience());
        assertEquals(originalMentor.isAvailable(), convertedMentor.isAvailable());
        assertEquals(originalMentor.getRole(), convertedMentor.getRole());
    }

    @Test
    void testToDTOWithMinimalData() {
        // Given
        Mentor mentor = new Mentor();
        mentor.setId(2L);
        mentor.setNom("Jane Smith");
        mentor.setEmail("jane@example.com");
        mentor.setAvailable(false);
        mentor.setRole("MENTOR");

        // When
        MentorDTO dto = MentorMapper.toDTO(mentor);

        // Then
        assertNotNull(dto);
        assertEquals(2L, dto.getId());
        assertEquals("Jane Smith", dto.getNom());
        assertEquals("jane@example.com", dto.getEmail());
        assertNull(dto.getCompetences());
        assertNull(dto.getExperience());
        assertFalse(dto.isAvailable());
        assertEquals("MENTOR", dto.getRole());
    }
}
