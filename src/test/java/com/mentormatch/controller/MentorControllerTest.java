package com.mentormatch.controller;

import com.mentormatch.dto.MentorDTO;
import com.mentormatch.service.MentorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MentorControllerTest {

    @Mock
    private MentorService mentorService;

    private MentorController mentorController;

    @BeforeEach
    void setUp() {
        mentorController = new MentorController(mentorService);
    }

    @Test
    void testGetAllMentors() {
        // Given
        MentorDTO mentor1 = createMentorDTO(1L, "John Doe", "john@example.com", "Java", "5 years", true);
        MentorDTO mentor2 = createMentorDTO(2L, "Jane Smith", "jane@example.com", "Angular", "3 years", true);
        List<MentorDTO> mentors = Arrays.asList(mentor1, mentor2);
        
        when(mentorService.findAll()).thenReturn(mentors);

        // When
        ResponseEntity<List<MentorDTO>> response = mentorController.getAllMentors(null, null, null);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(mentorService).findAll();
    }

    @Test
    void testGetAllMentorsWithAvailabilityFilter() {
        // Given
        Boolean available = true;
        MentorDTO mentor = createMentorDTO(1L, "John Doe", "john@example.com", "Java", "5 years", true);
        List<MentorDTO> mentors = Arrays.asList(mentor);
        
        when(mentorService.findByAvailability(available)).thenReturn(mentors);

        // When
        ResponseEntity<List<MentorDTO>> response = mentorController.getAllMentors(available, null, null);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(mentorService).findByAvailability(available);
    }

    @Test
    void testGetAllMentorsWithCompetencesFilter() {
        // Given
        String competences = "Java";
        MentorDTO mentor = createMentorDTO(1L, "John Doe", "john@example.com", "Java", "5 years", true);
        List<MentorDTO> mentors = Arrays.asList(mentor);
        
        when(mentorService.findByCompetences(competences)).thenReturn(mentors);

        // When
        ResponseEntity<List<MentorDTO>> response = mentorController.getAllMentors(null, competences, null);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(mentorService).findByCompetences(competences);
    }

    @Test
    void testGetAllMentorsWithSearchTerm() {
        // Given
        String searchTerm = "John";
        MentorDTO mentor = createMentorDTO(1L, "John Doe", "john@example.com", "Java", "5 years", true);
        List<MentorDTO> mentors = Arrays.asList(mentor);
        
        when(mentorService.searchMentors(searchTerm)).thenReturn(mentors);

        // When
        ResponseEntity<List<MentorDTO>> response = mentorController.getAllMentors(null, null, searchTerm);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(mentorService).searchMentors(searchTerm);
    }

    @Test
    void testGetMentorById() {
        // Given
        Long id = 1L;
        MentorDTO mentor = createMentorDTO(id, "John Doe", "john@example.com", "Java", "5 years", true);
        
        when(mentorService.findById(id)).thenReturn(Optional.of(mentor));

        // When
        ResponseEntity<MentorDTO> response = mentorController.getMentorById(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getNom());
        verify(mentorService).findById(id);
    }

    @Test
    void testGetMentorByIdNotFound() {
        // Given
        Long id = 999L;
        when(mentorService.findById(id)).thenReturn(Optional.empty());

        // When
        ResponseEntity<MentorDTO> response = mentorController.getMentorById(id);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(mentorService).findById(id);
    }

    @Test
    void testCreateMentor() {
        // Given
        MentorDTO mentorDTO = createMentorDTO(1L, "John Doe", "john@example.com", "Java", "5 years", true);
        MentorDTO createdMentor = createMentorDTO(1L, "John Doe", "john@example.com", "Java", "5 years", true);
        
        when(mentorService.save(any(MentorDTO.class))).thenReturn(createdMentor);

        // When
        ResponseEntity<MentorDTO> response = mentorController.createMentor(mentorDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getNom());
        verify(mentorService).save(any(MentorDTO.class));
    }

    @Test
    void testCreateMentorWithException() {
        // Given
        MentorDTO mentorDTO = createMentorDTO(1L, "John Doe", "john@example.com", "Java", "5 years", true);
        
        when(mentorService.save(any(MentorDTO.class))).thenThrow(new RuntimeException("Database error"));

        // When
        ResponseEntity<MentorDTO> response = mentorController.createMentor(mentorDTO);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(mentorService).save(any(MentorDTO.class));
    }

    @Test
    void testUpdateMentor() {
        // Given
        Long id = 1L;
        MentorDTO mentorDTO = createMentorDTO(id, "John Doe Updated", "john@example.com", "Java", "5 years", true);
        MentorDTO updatedMentor = createMentorDTO(id, "John Doe Updated", "john@example.com", "Java", "5 years", true);
        
        when(mentorService.save(any(MentorDTO.class))).thenReturn(updatedMentor);

        // When
        ResponseEntity<MentorDTO> response = mentorController.updateMentor(id, mentorDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe Updated", response.getBody().getNom());
        assertEquals(id, response.getBody().getId());
        verify(mentorService).save(any(MentorDTO.class));
    }

    @Test
    void testDeleteMentor() {
        // Given
        Long id = 1L;

        // When
        ResponseEntity<Void> response = mentorController.deleteMentor(id);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(mentorService).deleteById(id);
    }

    @Test
    void testDeleteMentorWithException() {
        // Given
        Long id = 999L;
        doThrow(new RuntimeException("Mentor not found")).when(mentorService).deleteById(id);

        // When
        ResponseEntity<Void> response = mentorController.deleteMentor(id);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(mentorService).deleteById(id);
    }

    @Test
    void testSearchMentors() {
        // Given
        String query = "Java";
        MentorDTO mentor = createMentorDTO(1L, "John Doe", "john@example.com", "Java", "5 years", true);
        List<MentorDTO> mentors = Arrays.asList(mentor);
        
        when(mentorService.searchMentors(query)).thenReturn(mentors);

        // When
        ResponseEntity<List<MentorDTO>> response = mentorController.searchMentors(query);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(mentorService).searchMentors(query);
    }

    @Test
    void testGetMentorsByCompetences() {
        // Given
        String competences = "Java";
        MentorDTO mentor = createMentorDTO(1L, "John Doe", "john@example.com", "Java", "5 years", true);
        List<MentorDTO> mentors = Arrays.asList(mentor);
        
        when(mentorService.findByCompetences(competences)).thenReturn(mentors);

        // When
        ResponseEntity<List<MentorDTO>> response = mentorController.getMentorsByCompetences(competences);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(mentorService).findByCompetences(competences);
    }

    private MentorDTO createMentorDTO(Long id, String nom, String email, String competences, String experience, boolean available) {
        MentorDTO dto = new MentorDTO();
        dto.setId(id);
        dto.setNom(nom);
        dto.setEmail(email);
        dto.setCompetences(competences);
        dto.setExperience(experience);
        dto.setAvailable(available);
        dto.setRole("MENTOR");
        return dto;
    }
}
