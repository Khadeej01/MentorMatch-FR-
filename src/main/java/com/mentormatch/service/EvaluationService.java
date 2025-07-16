package com.mentormatch.service;

import com.mentormatch.dto.EvaluationDTO;
import com.mentormatch.mapper.EvaluationMapper;
import com.mentormatch.model.Evaluation;
import com.mentormatch.repository.EvaluationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;

    public EvaluationService(EvaluationRepositoryevaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    public List<EvaluationDTO> findAll() {
        return evaluationRepository.findAll().stream()
                .map(EvaluationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<EvaluationDTO> findById(Long id) {
        return evaluationRepository.findById(id)
                .map(EvaluationMapper::toDTO);
    }

    public EvaluationDTO save(EvaluationDTO evaluationDTO) {
        Evaluation evaluation = EvaluationMapper.toEntity(evaluationDTO);
        Evaluation saved = evaluationRepository.save(evaluation);
        return EvaluationMapper.toDTO(saved);
    }

    public void deleteById(Long id) {
        evaluationRepository.deleteById(id);
    }
}