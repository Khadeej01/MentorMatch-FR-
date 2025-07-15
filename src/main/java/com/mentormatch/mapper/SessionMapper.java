package com.mentormatch.mapper;

import com.mentormatch.model.Session;
import com.mentormatch.dto.SessionDTO;

public class SessionMapper {
    public static SessionDTO toDTO(Session session) {
        SessionDTO dto = new SessionDTO();
        dto.setId(session.getId());
        dto.setDateHeure(session.getDateHeure());
        dto.setSujet(session.getSujet());
        dto.setMentorId(session.getMentorId());
        dto.setApprenantId(session.getApprenantId());
        return dto;
    }

    public static Session toEntity(SessionDTO dto) {
        Session session = new Session();
        session.setId(dto.getId());
        session.setDateHeure(dto.getDateHeure());
        session.setSujet(dto.getSujet());
        session.setMentorId(dto.getMentorId());
        session.setApprenantId(dto.getApprenantId());
        return session;
    }
} 