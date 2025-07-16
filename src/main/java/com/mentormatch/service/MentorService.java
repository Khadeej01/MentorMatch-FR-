package com.mentormatch.service;

import com.mentormatch.dto.MentorDTO;
import com.mentormatch.mapper.MentorMapper;
import com.mentormatch.model.Mentor;
import com.mentormatch.repository.MentorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MentorService {
    private final MentorRepository mentorRepository;

    public MentorService(MentorRepository mentorRepository) {
        this.mentorRepository = mentorRepository;
    }

    public List<MentorDTO> findAll() {
        return mentorRepository.findAll().stream()
                .map(MentorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<MentorDTO> findById(Long id) {
        return mentorRepository.findById(id)
                .map(MentorMapper::toDTO);
    }

    public MentorDTO save(MentorDTO mentorDTO) {
        Mentor mentor = MentorMapper.toEntity(mentorDTO);
        Mentor saved = mentorRepository.save(mentor);
        return MentorMapper.toDTO(saved);
    }

    public void deleteById(Long id) {
        mentorRepository.deleteById(id);
    }

    public List<MentorDTO> findByAvailability(boolean isAvailable) {
        return mentorRepository.findByIsAvailable(isAvailable)
                .stream()
                .map(MentorMapper::toDTO)
                .collect(Collectors.toList());
    }
} 