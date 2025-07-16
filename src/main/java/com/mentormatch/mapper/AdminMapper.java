package com.mentormatch.mapper;

import com.mentormatch.model.Admin;
import com.mentormatch.dto.AdminDTO;

public class AdminMapper {
    public static AdminDTO toDTO(Admin admin) {
        AdminDTO dto = new AdminDTO();
        dto.setId(admin.getId());
        dto.setNom(admin.getNom());
        dto.setEmail(admin.getEmail());
        dto.setRole(admin.getRole());
        return dto;
    }

    public static Admin toEntity(AdminDTO dto) {
        Admin admin = new Admin();
        admin.setId(dto.getId());
        admin.setNom(dto.getNom());
        admin.setEmail(dto.getEmail());
        admin.setRole(dto.getRole());
        return admin;
    }
}