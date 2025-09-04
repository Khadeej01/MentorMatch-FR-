package com.mentormatch.repository;

import com.mentormatch.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    // Trouver les réservations d'un mentor
    List<Booking> findByMentorIdOrderByDateTimeDesc(Long mentorId);
    
    // Trouver les réservations d'un apprenant
    List<Booking> findByApprenantIdOrderByDateTimeDesc(Long apprenantId);
    
    // Trouver les réservations par statut
    List<Booking> findByStatusOrderByDateTimeDesc(String status);
    
    // Trouver les réservations d'un mentor par statut
    List<Booking> findByMentorIdAndStatusOrderByDateTimeDesc(Long mentorId, String status);
    
    // Trouver les réservations d'un apprenant par statut
    List<Booking> findByApprenantIdAndStatusOrderByDateTimeDesc(Long apprenantId, String status);
    
    // Vérifier les conflits de réservation pour un mentor
    @Query("SELECT b FROM Booking b WHERE b.mentor.id = :mentorId AND b.status IN ('PENDING', 'CONFIRMED') " +
           "AND ((b.dateTime <= :startTime AND b.dateTime >= :endTime) OR " +
           "(b.dateTime >= :startTime AND b.dateTime <= :endTime))")
    List<Booking> findConflictingBookings(@Param("mentorId") Long mentorId, 
                                         @Param("startTime") LocalDateTime startTime, 
                                         @Param("endTime") LocalDateTime endTime);
    
    // Compter les réservations par mentor
    long countByMentorId(Long mentorId);
    
    // Compter les réservations par apprenant
    long countByApprenantId(Long apprenantId);
    
    // Trouver les réservations dans une période
    @Query("SELECT b FROM Booking b WHERE b.dateTime BETWEEN :startDate AND :endDate ORDER BY b.dateTime DESC")
    List<Booking> findBookingsBetweenDates(@Param("startDate") LocalDateTime startDate, 
                                          @Param("endDate") LocalDateTime endDate);
}
