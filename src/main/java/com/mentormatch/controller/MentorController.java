package com.mentormatch.controller;

import com.mentormatch.dto.MentorDTO;
import com.mentormatch.service.MentorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mentors")
@CrossOrigin(origins = "http://localhost:4200")
public class MentorController {
    private final MentorService mentorService;

    public MentorController(MentorService mentorService) {
        this.mentorService = mentorService;
    }

    @GetMapping
    public ResponseEntity<List<MentorDTO>> getAllMentors(
            @RequestParam(value = "available", required = false) Boolean available,
            @RequestParam(value = "competences", required = false) String competences,
            @RequestParam(value = "search", required = false) String searchTerm) {
        
        List<MentorDTO> mentors;
        
        if (available != null) {
            mentors = mentorService.findByAvailability(available);
        } else if (competences != null && !competences.isEmpty()) {
            mentors = mentorService.findByCompetences(competences);
        } else if (searchTerm != null && !searchTerm.isEmpty()) {
            mentors = mentorService.searchMentors(searchTerm);
        } else {
            mentors = mentorService.findAll();
        }
        
        return ResponseEntity.ok(mentors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentorDTO> getMentorById(@PathVariable Long id) {
        Optional<MentorDTO> mentor = mentorService.findById(id);
        return mentor.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MentorDTO> createMentor(@RequestBody MentorDTO mentorDTO) {
        try {
            MentorDTO created = mentorService.save(mentorDTO);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MentorDTO> updateMentor(@PathVariable Long id, @RequestBody MentorDTO mentorDTO) {
        try {
            mentorDTO.setId(id);
            MentorDTO updated = mentorService.save(mentorDTO);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMentor(@PathVariable Long id) {
        try {
            mentorService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<MentorDTO>> searchMentors(@RequestParam String q) {
        List<MentorDTO> mentors = mentorService.searchMentors(q);
        return ResponseEntity.ok(mentors);
    }

    @GetMapping("/competences/{competences}")
    public ResponseEntity<List<MentorDTO>> getMentorsByCompetences(@PathVariable String competences) {
        List<MentorDTO> mentors = mentorService.findByCompetences(competences);
        return ResponseEntity.ok(mentors);
    }
} 