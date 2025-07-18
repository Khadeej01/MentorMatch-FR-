package com.mentormatch.repository;

import com.mentormatch.model.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentorRepository extends JpaRepository<Mentor, Long> {
    List<Mentor> findByIsAvailable(boolean isAvailable);
} 