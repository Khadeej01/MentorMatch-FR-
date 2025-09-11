package com.mentormatch.controller;

import com.mentormatch.dto.MentorDTO;
import com.mentormatch.service.MentorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mentors")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Mentors", description = "API pour la gestion des mentors")
public class MentorController {
    private final MentorService mentorService;

    public MentorController(MentorService mentorService) {
        this.mentorService = mentorService;
    }

    @Operation(summary = "Récupérer tous les mentors", description = "Récupère la liste de tous les mentors avec filtres optionnels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des mentors récupérée avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping
    public ResponseEntity<List<MentorDTO>> getAllMentors(
            @Parameter(description = "Filtrer par disponibilité") @RequestParam(value = "available", required = false) Boolean available,
            @Parameter(description = "Filtrer par compétences") @RequestParam(value = "competences", required = false) String competences,
            @Parameter(description = "Terme de recherche") @RequestParam(value = "search", required = false) String searchTerm) {
        
        List<MentorDTO> mentors;
        
        if (available != null) {
            mentors = mentorService.findByAvailability(available);
        } else if (competences != null && !competences.isEmpty()) {
            mentors = mentorService.findByCompetences(competences);
        } else if (searchTerm != null && !searchTerm.isEmpty()) {
            mentors = mentorService.searchMentors(searchTerm);
        } else {
            mentors = mentorService.findAll();
        }
        
        return ResponseEntity.ok(mentors);
    }

    @Operation(summary = "Récupérer un mentor par ID", description = "Récupère les détails d'un mentor spécifique par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mentor trouvé avec succès"),
            @ApiResponse(responseCode = "404", description = "Mentor non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MentorDTO> getMentorById(
            @Parameter(description = "ID du mentor à récupérer") @PathVariable Long id) {
        Optional<MentorDTO> mentor = mentorService.findById(id);
        return mentor.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Créer un nouveau mentor", description = "Crée un nouveau mentor dans le système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mentor créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ResponseEntity<MentorDTO> createMentor(
            @Parameter(description = "Données du mentor à créer") @RequestBody MentorDTO mentorDTO) {
        try {
            MentorDTO created = mentorService.save(mentorDTO);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Mettre à jour un mentor", description = "Met à jour les informations d'un mentor existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mentor mis à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Mentor non trouvé")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{id}")
    public ResponseEntity<MentorDTO> updateMentor(
            @Parameter(description = "ID du mentor à mettre à jour") @PathVariable Long id,
            @Parameter(description = "Nouvelles données du mentor") @RequestBody MentorDTO mentorDTO) {
        try {
            mentorDTO.setId(id);
            MentorDTO updated = mentorService.save(mentorDTO);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Supprimer un mentor", description = "Supprime un mentor du système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Mentor supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Mentor non trouvé")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMentor(
            @Parameter(description = "ID du mentor à supprimer") @PathVariable Long id) {
        try {
            mentorService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Rechercher des mentors", description = "Recherche des mentors par terme de recherche")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recherche effectuée avec succès")
    })
    @GetMapping("/search")
    public ResponseEntity<List<MentorDTO>> searchMentors(
            @Parameter(description = "Terme de recherche") @RequestParam String q) {
        List<MentorDTO> mentors = mentorService.searchMentors(q);
        return ResponseEntity.ok(mentors);
    }

    @Operation(summary = "Rechercher par compétences", description = "Recherche des mentors par compétences spécifiques")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recherche effectuée avec succès")
    })
    @GetMapping("/competences/{competences}")
    public ResponseEntity<List<MentorDTO>> getMentorsByCompetences(
            @Parameter(description = "Compétences à rechercher") @PathVariable String competences) {
        List<MentorDTO> mentors = mentorService.findByCompetences(competences);
        return ResponseEntity.ok(mentors);
    }
} 