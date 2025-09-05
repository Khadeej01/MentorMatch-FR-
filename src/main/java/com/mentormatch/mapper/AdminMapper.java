package com.mentormatch.mapper;

import com.mentormatch.model.Admin;
import com.mentormatch.dto.AdminDTO;

public class AdminMapper {
    public static AdminDTO toDTO(Admin admin) {
        if (admin == null) {
            return null;
        }
        
        AdminDTO dto = new AdminDTO();
        dto.setId(admin.getId());
        dto.setUsername(admin.getUsername());
        dto.setEmail(admin.getEmail());
        dto.setRole(admin.getRole());
        return dto;
    }

    public static Admin toEntity(AdminDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Admin admin = new Admin();
        admin.setId(dto.getId());
        admin.setUsername(dto.getUsername());
        admin.setEmail(dto.getEmail());
        admin.setRole(dto.getRole());
        return admin;
    }
}