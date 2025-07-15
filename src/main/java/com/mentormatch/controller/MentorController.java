package com.mentormatch.controller;

import com.mentormatch.dto.MentorDTO;
import com.mentormatch.mapper.MentorMapper;
import com.mentormatch.model.Mentor;
import com.mentormatch.service.MentorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mentors")
public class MentorController {
    private final MentorService mentorService;

    public MentorController(MentorService mentorService) {
        this.mentorService = mentorService;
    }

    @GetMapping
    public List<MentorDTO> getAllMentors() {
        return mentorService.findAll().stream()
                .map(MentorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MentorDTO getMentorById(@PathVariable Long id) {
        Optional<Mentor> mentor = mentorService.findById(id);
        return mentor.map(MentorMapper::toDTO).orElse(null);
    }

    @PostMapping
    public MentorDTO createMentor(@RequestBody MentorDTO mentorDTO) {
        Mentor mentor = MentorMapper.toEntity(mentorDTO);
        Mentor savedMentor = mentorService.save(mentor);
        return MentorMapper.toDTO(savedMentor);
    }

    @PutMapping("/{id}")
    public MentorDTO updateMentor(@PathVariable Long id, @RequestBody MentorDTO mentorDTO) {
        mentorDTO.setId(id);
        Mentor mentor = MentorMapper.toEntity(mentorDTO);
        Mentor updatedMentor = mentorService.save(mentor);
        return MentorMapper.toDTO(updatedMentor);
    }

    @DeleteMapping("/{id}")
    public void deleteMentor(@PathVariable Long id) {
        mentorService.deleteById(id);
    }
} 