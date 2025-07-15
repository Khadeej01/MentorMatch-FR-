package com.mentormatch.service;

import com.mentormatch.model.Apprenant;
import com.mentormatch.repository.ApprenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApprenantService {
    private final ApprenantRepository apprenantRepository;

    public ApprenantService(ApprenantRepository apprenantRepository) {
        this.apprenantRepository = apprenantRepository;
    }

    public List<Apprenant> findAll() {
        return apprenantRepository.findAll();
    }

    public Optional<Apprenant> findById(Long id) {
        return apprenantRepository.findById(id);
    }

    public Apprenant save(Apprenant apprenant) {
        return apprenantRepository.save(apprenant);
    }

    public void deleteById(Long id) {
        apprenantRepository.deleteById(id);
    }
} 