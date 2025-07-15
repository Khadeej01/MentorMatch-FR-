package com.mentormatch.service;

import com.mentormatch.dto.SessionDTO;
import com.mentormatch.mapper.SessionMapper;
import com.mentormatch.model.Session;
import com.mentormatch.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<SessionDTO> findAll() {
        return sessionRepository.findAll().stream()
                .map(SessionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<SessionDTO> findById(Long id) {
        return sessionRepository.findById(id)
                .map(SessionMapper::toDTO);
    }

    public SessionDTO save(SessionDTO sessionDTO) {
        Session session = SessionMapper.toEntity(sessionDTO);
        Session saved = sessionRepository.save(session);
        return SessionMapper.toDTO(saved);
    }

    public void deleteById(Long id) {
        sessionRepository.deleteById(id);
    }
} 