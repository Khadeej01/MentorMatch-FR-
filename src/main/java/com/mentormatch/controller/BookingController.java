package com.mentormatch.controller;

import com.mentormatch.dto.BookingDTO;
import com.mentormatch.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:4200")
public class BookingController {
    
    private final BookingService bookingService;
    
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    
    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<BookingDTO> bookings = bookingService.findAll();
        return ResponseEntity.ok(bookings);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id) {
        Optional<BookingDTO> booking = bookingService.findById(id);
        return booking.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/mentor/{mentorId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByMentor(@PathVariable Long mentorId) {
        List<BookingDTO> bookings = bookingService.findByMentorId(mentorId);
        return ResponseEntity.ok(bookings);
    }
    
    @GetMapping("/apprenant/{apprenantId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByApprenant(@PathVariable Long apprenantId) {
        List<BookingDTO> bookings = bookingService.findByApprenantId(apprenantId);
        return ResponseEntity.ok(bookings);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<BookingDTO>> getBookingsByStatus(@PathVariable String status) {
        List<BookingDTO> bookings = bookingService.findByStatus(status);
        return ResponseEntity.ok(bookings);
    }
    
    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO) {
        try {
            BookingDTO created = bookingService.createBooking(bookingDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Long id, @RequestBody BookingDTO bookingDTO) {
        try {
            BookingDTO updated = bookingService.updateBooking(id, bookingDTO);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<BookingDTO> updateBookingStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            BookingDTO updated = bookingService.updateBookingStatus(id, status);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        try {
            bookingService.deleteBooking(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/mentor/{mentorId}/count")
    public ResponseEntity<Long> getBookingCountByMentor(@PathVariable Long mentorId) {
        long count = bookingService.countBookingsByMentor(mentorId);
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/apprenant/{apprenantId}/count")
    public ResponseEntity<Long> getBookingCountByApprenant(@PathVariable Long apprenantId) {
        long count = bookingService.countBookingsByApprenant(apprenantId);
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/between")
    public ResponseEntity<List<BookingDTO>> getBookingsBetweenDates(
            @RequestParam String startDate, 
            @RequestParam String endDate) {
        try {
            LocalDateTime start = LocalDateTime.parse(startDate);
            LocalDateTime end = LocalDateTime.parse(endDate);
            List<BookingDTO> bookings = bookingService.findBookingsBetweenDates(start, end);
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
