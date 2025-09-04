package com.mentormatch.controller;

import com.mentormatch.dto.SessionDTO;
import com.mentormatch.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sessions")
@CrossOrigin(origins = "http://localhost:4200")
public class SessionController {
    
    private final SessionService sessionService;
    
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }
    
    @GetMapping
    public ResponseEntity<List<SessionDTO>> getAllSessions() {
        List<SessionDTO> sessions = sessionService.findAll();
        return ResponseEntity.ok(sessions);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SessionDTO> getSessionById(@PathVariable Long id) {
        Optional<SessionDTO> session = sessionService.findById(id);
        return session.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/mentor/{mentorId}")
    public ResponseEntity<List<SessionDTO>> getSessionsByMentor(@PathVariable Long mentorId) {
        List<SessionDTO> sessions = sessionService.findByMentorId(mentorId);
        return ResponseEntity.ok(sessions);
    }
    
    @GetMapping("/apprenant/{apprenantId}")
    public ResponseEntity<List<SessionDTO>> getSessionsByApprenant(@PathVariable Long apprenantId) {
        List<SessionDTO> sessions = sessionService.findByApprenantId(apprenantId);
        return ResponseEntity.ok(sessions);
    }
    
    @PostMapping
    public ResponseEntity<SessionDTO> createSession(@RequestBody SessionDTO sessionDTO) {
        try {
            SessionDTO created = sessionService.save(sessionDTO);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SessionDTO> updateSession(@PathVariable Long id, @RequestBody SessionDTO sessionDTO) {
        try {
            sessionDTO.setId(id);
            SessionDTO updated = sessionService.save(sessionDTO);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        try {
            sessionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}