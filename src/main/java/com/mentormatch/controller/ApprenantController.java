package com.mentormatch.controller;

import com.mentormatch.dto.ApprenantDTO;
import com.mentormatch.service.ApprenantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/apprenants")
@CrossOrigin(origins = "http://localhost:4200")
public class ApprenantController {
    private final ApprenantService apprenantService;

    public ApprenantController(ApprenantService apprenantService) {
        this.apprenantService = apprenantService;
    }

    @GetMapping
    public ResponseEntity<List<ApprenantDTO>> getAllApprenants() {
        List<ApprenantDTO> apprenants = apprenantService.findAll();
        return ResponseEntity.ok(apprenants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApprenantDTO> getApprenantById(@PathVariable Long id) {
        Optional<ApprenantDTO> apprenant = apprenantService.findById(id);
        return apprenant.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ApprenantDTO> createApprenant(@RequestBody ApprenantDTO apprenantDTO) {
        try {
            ApprenantDTO created = apprenantService.save(apprenantDTO);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApprenantDTO> updateApprenant(@PathVariable Long id, @RequestBody ApprenantDTO apprenantDTO) {
        try {
            apprenantDTO.setId(id);
            ApprenantDTO updated = apprenantService.save(apprenantDTO);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApprenant(@PathVariable Long id) {
        try {
            apprenantService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
} 