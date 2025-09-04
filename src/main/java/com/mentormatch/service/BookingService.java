package com.mentormatch.service;

import com.mentormatch.dto.BookingDTO;
import com.mentormatch.mapper.BookingMapper;
import com.mentormatch.model.Booking;
import com.mentormatch.model.Mentor;
import com.mentormatch.model.Apprenant;
import com.mentormatch.repository.BookingRepository;
import com.mentormatch.repository.MentorRepository;
import com.mentormatch.repository.ApprenantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingService {
    
    private final BookingRepository bookingRepository;
    private final MentorRepository mentorRepository;
    private final ApprenantRepository apprenantRepository;
    
    public BookingService(BookingRepository bookingRepository, 
                         MentorRepository mentorRepository, 
                         ApprenantRepository apprenantRepository) {
        this.bookingRepository = bookingRepository;
        this.mentorRepository = mentorRepository;
        this.apprenantRepository = apprenantRepository;
    }
    
    public List<BookingDTO> findAll() {
        return bookingRepository.findAll().stream()
                .map(BookingMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public Optional<BookingDTO> findById(Long id) {
        return bookingRepository.findById(id)
                .map(BookingMapper::toDTO);
    }
    
    public List<BookingDTO> findByMentorId(Long mentorId) {
        return bookingRepository.findByMentorIdOrderByDateTimeDesc(mentorId)
                .stream()
                .map(BookingMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<BookingDTO> findByApprenantId(Long apprenantId) {
        return bookingRepository.findByApprenantIdOrderByDateTimeDesc(apprenantId)
                .stream()
                .map(BookingMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<BookingDTO> findByStatus(String status) {
        return bookingRepository.findByStatusOrderByDateTimeDesc(status)
                .stream()
                .map(BookingMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        // Vérifier que le mentor et l'apprenant existent
        Mentor mentor = mentorRepository.findById(bookingDTO.getMentorId())
                .orElseThrow(() -> new RuntimeException("Mentor not found"));
        
        Apprenant apprenant = apprenantRepository.findById(bookingDTO.getApprenantId())
                .orElseThrow(() -> new RuntimeException("Apprenant not found"));
        
        // Vérifier les conflits de réservation
        LocalDateTime startTime = bookingDTO.getDateTime().minusHours(1);
        LocalDateTime endTime = bookingDTO.getDateTime().plusHours(1);
        
        List<Booking> conflicts = bookingRepository.findConflictingBookings(
                bookingDTO.getMentorId(), startTime, endTime);
        
        if (!conflicts.isEmpty()) {
            throw new RuntimeException("Time slot conflict: Mentor is not available at this time");
        }
        
        // Créer la réservation
        Booking booking = new Booking(mentor, apprenant, bookingDTO.getDateTime());
        booking.setNotes(bookingDTO.getNotes());
        booking.setStatus("PENDING");
        
        Booking saved = bookingRepository.save(booking);
        return BookingMapper.toDTO(saved);
    }
    
    public BookingDTO updateBookingStatus(Long id, String status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        
        booking.setStatus(status);
        booking.setUpdatedAt(LocalDateTime.now());
        
        Booking saved = bookingRepository.save(booking);
        return BookingMapper.toDTO(saved);
    }
    
    public BookingDTO updateBooking(Long id, BookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        
        booking.setDateTime(bookingDTO.getDateTime());
        booking.setNotes(bookingDTO.getNotes());
        booking.setUpdatedAt(LocalDateTime.now());
        
        Booking saved = bookingRepository.save(booking);
        return BookingMapper.toDTO(saved);
    }
    
    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new RuntimeException("Booking not found");
        }
        bookingRepository.deleteById(id);
    }
    
    public long countBookingsByMentor(Long mentorId) {
        return bookingRepository.countByMentorId(mentorId);
    }
    
    public long countBookingsByApprenant(Long apprenantId) {
        return bookingRepository.countByApprenantId(apprenantId);
    }
    
    public List<BookingDTO> findBookingsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return bookingRepository.findBookingsBetweenDates(startDate, endDate)
                .stream()
                .map(BookingMapper::toDTO)
                .collect(Collectors.toList());
    }
}
