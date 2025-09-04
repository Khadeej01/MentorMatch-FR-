package com.mentormatch.repository;

import com.mentormatch.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    
    List<Session> findByMentorIdOrderByDateHeureDesc(Long mentorId);
    List<Session> findByApprenantIdOrderByDateHeureDesc(Long apprenantId);
    
    @Query("SELECT COUNT(s) FROM Session s WHERE s.mentorId = :mentorId")
    int countSessionsByMentorId(@Param("mentorId") Long mentorId);
    
    @Query("SELECT COUNT(s) FROM Session s WHERE s.apprenantId = :apprenantId")
    int countSessionsByApprenantId(@Param("apprenantId") Long apprenantId);
    
    @Query("SELECT COUNT(s) FROM Session s WHERE s.mentorId = :mentorId AND s.sujet = :sujet")
    int countSessionsByMentorName(@Param("mentorId") Long mentorId, @Param("sujet") String sujet);
}