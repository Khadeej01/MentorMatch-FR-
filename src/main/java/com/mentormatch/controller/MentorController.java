package com.mentormatch.controller;

import com.mentormatch.dto.MentorDTO;
import com.mentormatch.service.MentorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mentors")
public class MentorController {
    private final MentorService mentorService;

    public MentorController(MentorService mentorService) {
        this.mentorService = mentorService;
    }

    @GetMapping
    public List<MentorDTO> getAllMentors(@RequestParam(value = "available", required = false) Boolean available) {
        if (available != null) {
            return mentorService.findByAvailability(available);
        }
        return mentorService.findAll();
    }

    @GetMapping("/{id}")
    public MentorDTO getMentorById(@PathVariable Long id) {
        Optional<MentorDTO> mentor = mentorService.findById(id);
        return mentor.orElse(null);
    }

    @PostMapping
    public MentorDTO createMentor(@RequestBody MentorDTO mentorDTO) {
        return mentorService.save(mentorDTO);
    }

    @PutMapping("/{id}")
    public MentorDTO updateMentor(@PathVariable Long id, @RequestBody MentorDTO mentorDTO) {
        mentorDTO.setId(id);
        return mentorService.save(mentorDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteMentor(@PathVariable Long id) {
        mentorService.deleteById(id);
    }
} 