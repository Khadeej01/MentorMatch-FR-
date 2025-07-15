package com.mentormatch.controller;

import com.mentormatch.dto.ApprenantDTO;
import com.mentormatch.service.ApprenantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/apprenants")
public class ApprenantController {
    private final ApprenantService apprenantService;

    public ApprenantController(ApprenantService apprenantService) {
        this.apprenantService = apprenantService;
    }

    @GetMapping
    public List<ApprenantDTO> getAllApprenants() {
        return apprenantService.findAll();
    }

    @GetMapping("/{id}")
    public ApprenantDTO getApprenantById(@PathVariable Long id) {
        Optional<ApprenantDTO> apprenant = apprenantService.findById(id);
        return apprenant.orElse(null);
    }

    @PostMapping
    public ApprenantDTO createApprenant(@RequestBody ApprenantDTO apprenantDTO) {
        return apprenantService.save(apprenantDTO);
    }

    @PutMapping("/{id}")
    public ApprenantDTO updateApprenant(@PathVariable Long id, @RequestBody ApprenantDTO apprenantDTO) {
        apprenantDTO.setId(id);
        return apprenantService.save(apprenantDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteApprenant(@PathVariable Long id) {
        apprenantService.deleteById(id);
    }
} 