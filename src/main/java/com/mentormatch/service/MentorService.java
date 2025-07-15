package com.mentormatch.service;

import com.mentormatch.model.Mentor;
import com.mentormatch.repository.MentorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MentorService {
    private final MentorRepository mentorRepository;

    public MentorService(MentorRepository mentorRepository) {
        this.mentorRepository = mentorRepository;
    }

    public List<Mentor> findAll() {
        return mentorRepository.findAll();
    }

    public Optional<Mentor> findById(Long id) {
        return mentorRepository.findById(id);
    }

    public Mentor save(Mentor mentor) {
        return mentorRepository.save(mentor);
    }

    public void deleteById(Long id) {
        mentorRepository.deleteById(id);
    }
} 