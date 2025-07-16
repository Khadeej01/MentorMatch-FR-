package com.mentormatch.mapper;

import com.mentormatch.model.Evaluation;
import com.mentormatch.dto.EvaluationDTO;

public class EvaluationMapper {
    public static EvaluationDTO toDTO(Evaluation evaluation) {
        EvaluationDTO dto = new EvaluationDTO();
        dto.setId(evaluation.getId());
        dto.setNote(evaluation.getNote());
        dto.setCommentaire(evaluation.getCommentaire());
        dto.setSessionId(evaluation.getSessionId());
        dto.setDate(evaluation.getDate());
        return dto;
    }

    public static Evaluation toEntity(EvaluationDTO dto) {
        Evaluation evaluation = new Evaluation();
        evaluation.setId(dto.getId());
        evaluation.setNote(dto.getNote());
        evaluation.setCommentaire(dto.getCommentaire());
        evaluation.setSessionId(dto.getSessionId());
        evaluation.setDate(dto.getDate());
        return evaluation;
    }
}