package com.mentormatch.service;

import com.mentormatch.dto.ApprenantDTO;
import com.mentormatch.mapper.ApprenantMapper;
import com.mentormatch.model.Apprenant;
import com.mentormatch.repository.ApprenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApprenantService {
    private final ApprenantRepository apprenantRepository;

    public ApprenantService(ApprenantRepository apprenantRepository) {
        this.apprenantRepository = apprenantRepository;
    }

    public List<ApprenantDTO> findAll() {
        return apprenantRepository.findAll().stream()
                .map(ApprenantMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ApprenantDTO> findById(Long id) {
        return apprenantRepository.findById(id)
                .map(ApprenantMapper::toDTO);
    }

    public ApprenantDTO save(ApprenantDTO apprenantDTO) {
        Apprenant apprenant = ApprenantMapper.toEntity(apprenantDTO);
        Apprenant saved = apprenantRepository.save(apprenant);
        return ApprenantMapper.toDTO(saved);
    }

    public void deleteById(Long id) {
        apprenantRepository.deleteById(id);
    }
} 