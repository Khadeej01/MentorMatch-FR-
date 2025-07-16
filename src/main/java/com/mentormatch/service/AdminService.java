package com.mentormatch.service;

import com.mentormatch.dto.AdminDTO;
import com.mentormatch.mapper.AdminMapper;
import com.mentormatch.model.Admin;
import com.mentormatch.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<AdminDTO> findAll() {
        return adminRepository.findAll().stream()
                .map(AdminMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<AdminDTO> findById(Long id) {
        return adminRepository.findById(id)
                .map(AdminMapper::toDTO);
    }

    public AdminDTO save(AdminDTO adminDTO) {
        Admin admin = AdminMapper.toEntity(adminDTO);
        Admin saved = adminRepository.save(admin);
        return AdminMapper.toDTO(saved);
    }

    public void deleteById(Long id) {
        adminRepository.deleteById(id);
    }
}