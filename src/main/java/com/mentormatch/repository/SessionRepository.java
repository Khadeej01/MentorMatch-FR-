package com.mentormatch.repository;

import com.mentormatch.model.Session;
import lombok.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query(value = "select count(s.id) from Session s join Mentor m on s.mentorId = m.id where m.nom = :mentorName", nativeQuery = true)
    int  countSessionsByMentorName(@Param("mentorName") String mentorName);
}