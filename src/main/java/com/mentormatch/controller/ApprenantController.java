package com.mentormatch.controller;

import com.mentormatch.dto.ApprenantDTO;
import com.mentormatch.mapper.ApprenantMapper;
import com.mentormatch.model.Apprenant;
import com.mentormatch.service.ApprenantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/apprenants")
public class ApprenantController {
    private final ApprenantService apprenantService;

    public ApprenantController(ApprenantService apprenantService) {
        this.apprenantService = apprenantService;
    }

    @GetMapping
    public List<ApprenantDTO> getAllApprenants() {
        return apprenantService.findAll().stream()
                .map(ApprenantMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ApprenantDTO getApprenantById(@PathVariable Long id) {
        Optional<Apprenant> apprenant = apprenantService.findById(id);
        return apprenant.map(ApprenantMapper::toDTO).orElse(null);
    }

    @PostMapping
    public ApprenantDTO createApprenant(@RequestBody ApprenantDTO apprenantDTO) {
        Apprenant apprenant = ApprenantMapper.toEntity(apprenantDTO);
        Apprenant savedApprenant = apprenantService.save(apprenant);
        return ApprenantMapper.toDTO(savedApprenant);
    }

    @PutMapping("/{id}")
    public ApprenantDTO updateApprenant(@PathVariable Long id, @RequestBody ApprenantDTO apprenantDTO) {
        apprenantDTO.setId(id);
        Apprenant apprenant = ApprenantMapper.toEntity(apprenantDTO);
        Apprenant updatedApprenant = apprenantService.save(apprenant);
        return ApprenantMapper.toDTO(updatedApprenant);
    }

    @DeleteMapping("/{id}")
    public void deleteApprenant(@PathVariable Long id) {
        apprenantService.deleteById(id);
    }
} 