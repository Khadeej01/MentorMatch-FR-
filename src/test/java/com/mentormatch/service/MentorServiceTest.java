package com.mentormatch.service;

import com.mentormatch.dto.MentorDTO;
import com.mentormatch.mapper.MentorMapper;
import com.mentormatch.model.Mentor;
import com.mentormatch.repository.MentorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MentorServiceTest {

    @Mock
    private MentorRepository mentorRepository;

    private MentorService mentorService;

    @BeforeEach
    void setUp() {
        mentorService = new MentorService(mentorRepository);
    }

    @Test
    void testFindAll() {
        // Given
        Mentor mentor1 = createMentor(1L, "John Doe", "john@example.com", "Java, Spring", "5 years", true);
        Mentor mentor2 = createMentor(2L, "Jane Smith", "jane@example.com", "Angular, TypeScript", "3 years", true);
        List<Mentor> mentors = Arrays.asList(mentor1, mentor2);
        
        when(mentorRepository.findAll()).thenReturn(mentors);

        // When
        List<MentorDTO> result = mentorService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getNom());
        assertEquals("Jane Smith", result.get(1).getNom());
        verify(mentorRepository).findAll();
    }

    @Test
    void testFindById() {
        // Given
        Long id = 1L;
        Mentor mentor = createMentor(id, "John Doe", "john@example.com", "Java, Spring", "5 years", true);
        when(mentorRepository.findById(id)).thenReturn(Optional.of(mentor));

        // When
        Optional<MentorDTO> result = mentorService.findById(id);

        // Then
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getNom());
        assertEquals("john@example.com", result.get().getEmail());
        verify(mentorRepository).findById(id);
    }

    @Test
    void testFindByIdNotFound() {
        // Given
        Long id = 999L;
        when(mentorRepository.findById(id)).thenReturn(Optional.empty());

        // When
        Optional<MentorDTO> result = mentorService.findById(id);

        // Then
        assertFalse(result.isPresent());
        verify(mentorRepository).findById(id);
    }

    @Test
    void testSave() {
        // Given
        MentorDTO mentorDTO = createMentorDTO(1L, "John Doe", "john@example.com", "Java, Spring", "5 years", true);
        Mentor mentor = MentorMapper.toEntity(mentorDTO);
        Mentor savedMentor = createMentor(1L, "John Doe", "john@example.com", "Java, Spring", "5 years", true);
        
        when(mentorRepository.save(any(Mentor.class))).thenReturn(savedMentor);

        // When
        MentorDTO result = mentorService.save(mentorDTO);

        // Then
        assertNotNull(result);
        assertEquals("John Doe", result.getNom());
        assertEquals("john@example.com", result.getEmail());
        verify(mentorRepository).save(any(Mentor.class));
    }

    @Test
    void testDeleteById() {
        // Given
        Long id = 1L;

        // When
        mentorService.deleteById(id);

        // Then
        verify(mentorRepository).deleteById(id);
    }

    @Test
    void testFindByAvailability() {
        // Given
        boolean isAvailable = true;
        Mentor mentor1 = createMentor(1L, "John Doe", "john@example.com", "Java, Spring", "5 years", true);
        Mentor mentor2 = createMentor(2L, "Jane Smith", "jane@example.com", "Angular, TypeScript", "3 years", true);
        List<Mentor> mentors = Arrays.asList(mentor1, mentor2);
        
        when(mentorRepository.findByIsAvailable(isAvailable)).thenReturn(mentors);

        // When
        List<MentorDTO> result = mentorService.findByAvailability(isAvailable);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(MentorDTO::isAvailable));
        verify(mentorRepository).findByIsAvailable(isAvailable);
    }

    @Test
    void testFindByCompetences() {
        // Given
        String competences = "Java";
        Mentor mentor = createMentor(1L, "John Doe", "john@example.com", "Java, Spring", "5 years", true);
        List<Mentor> mentors = Arrays.asList(mentor);
        
        when(mentorRepository.findByCompetencesContainingIgnoreCase(competences)).thenReturn(mentors);

        // When
        List<MentorDTO> result = mentorService.findByCompetences(competences);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getNom());
        verify(mentorRepository).findByCompetencesContainingIgnoreCase(competences);
    }

    @Test
    void testSearchMentors() {
        // Given
        String searchTerm = "John";
        Mentor mentor = createMentor(1L, "John Doe", "john@example.com", "Java, Spring", "5 years", true);
        List<Mentor> mentors = Arrays.asList(mentor);
        
        when(mentorRepository.findByNomContainingIgnoreCaseOrCompetencesContainingIgnoreCase(searchTerm, searchTerm))
                .thenReturn(mentors);

        // When
        List<MentorDTO> result = mentorService.searchMentors(searchTerm);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getNom());
        verify(mentorRepository).findByNomContainingIgnoreCaseOrCompetencesContainingIgnoreCase(searchTerm, searchTerm);
    }

    private Mentor createMentor(Long id, String nom, String email, String competences, String experience, boolean available) {
        Mentor mentor = new Mentor();
        mentor.setId(id);
        mentor.setNom(nom);
        mentor.setEmail(email);
        mentor.setCompetences(competences);
        mentor.setExperience(experience);
        mentor.setAvailable(available);
        mentor.setRole("MENTOR");
        return mentor;
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
