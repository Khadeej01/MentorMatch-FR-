package com.mentormatch.controller;

import com.mentormatch.dto.AdminDTO;
import com.mentormatch.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public List<AdminDTO> getAllAdmins() {
        return adminService.findAll();
    }

    @GetMapping("/{id}")
    public AdminDTO getAdminById(@PathVariable Long id) {
        Optional<AdminDTO> admin = adminService.findById(id);
        return admin.orElse(null);
    }

    @PostMapping
    public AdminDTO createAdmin(@RequestBody AdminDTO adminDTO) {
        return adminService.save(adminDTO);
    }

    @PutMapping("/{id}")
    public AdminDTO updateAdmin(@PathVariable Long id, @RequestBody AdminDTO adminDTO) {
        adminDTO.setId(id);
        return adminService.save(adminDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable Long id) {
        adminService.deleteById(id);
    }
}