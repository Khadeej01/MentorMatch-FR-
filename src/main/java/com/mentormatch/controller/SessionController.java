package com.mentormatch.controller;

import com.mentormatch.dto.SessionDTO;
import com.mentormatch.service.SessionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public List<SessionDTO> getAllSessions() {
        return sessionService.findAll();
    }

    @GetMapping("/{id}")
    public SessionDTO getSessionById(@PathVariable Long id) {
        Optional<SessionDTO> session = sessionService.findById(id);
        return session.orElse(null);
    }

    @PostMapping
    public SessionDTO createSession(@RequestBody SessionDTO sessionDTO) {
        return sessionService.save(sessionDTO);
    }

    @PutMapping("/{id}")
    public SessionDTO updateSession(@PathVariable Long id, @RequestBody SessionDTO sessionDTO) {
        sessionDTO.setId(id);
        return sessionService.save(sessionDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteSession(@PathVariable Long id) {
        sessionService.deleteById(id);
    }
} 