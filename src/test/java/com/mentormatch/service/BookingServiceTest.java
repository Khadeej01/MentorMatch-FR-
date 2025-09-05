package com.mentormatch.service;

import com.mentormatch.dto.BookingDTO;
import com.mentormatch.model.Apprenant;
import com.mentormatch.model.Booking;
import com.mentormatch.model.Mentor;
import com.mentormatch.repository.ApprenantRepository;
import com.mentormatch.repository.BookingRepository;
import com.mentormatch.repository.MentorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private MentorRepository mentorRepository;

    @Mock
    private ApprenantRepository apprenantRepository;

    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        bookingService = new BookingService(bookingRepository, mentorRepository, apprenantRepository);
    }

    @Test
    void testFindAll() {
        // Given
        Booking booking1 = createBooking(1L, LocalDateTime.now().plusDays(1), "PENDING");
        Booking booking2 = createBooking(2L, LocalDateTime.now().plusDays(2), "CONFIRMED");
        List<Booking> bookings = Arrays.asList(booking1, booking2);
        
        when(bookingRepository.findAll()).thenReturn(bookings);

        // When
        List<BookingDTO> result = bookingService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookingRepository).findAll();
    }

    @Test
    void testFindById() {
        // Given
        Long id = 1L;
        Booking booking = createBooking(id, LocalDateTime.now().plusDays(1), "PENDING");
        when(bookingRepository.findById(id)).thenReturn(Optional.of(booking));

        // When
        Optional<BookingDTO> result = bookingService.findById(id);

        // Then
        assertTrue(result.isPresent());
        assertEquals("PENDING", result.get().getStatus());
        verify(bookingRepository).findById(id);
    }

    @Test
    void testFindByMentorId() {
        // Given
        Long mentorId = 1L;
        Booking booking = createBooking(1L, LocalDateTime.now().plusDays(1), "PENDING");
        List<Booking> bookings = Arrays.asList(booking);
        
        when(bookingRepository.findByMentorIdOrderByDateTimeDesc(mentorId)).thenReturn(bookings);

        // When
        List<BookingDTO> result = bookingService.findByMentorId(mentorId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookingRepository).findByMentorIdOrderByDateTimeDesc(mentorId);
    }

    @Test
    void testFindByApprenantId() {
        // Given
        Long apprenantId = 1L;
        Booking booking = createBooking(1L, LocalDateTime.now().plusDays(1), "PENDING");
        List<Booking> bookings = Arrays.asList(booking);
        
        when(bookingRepository.findByApprenantIdOrderByDateTimeDesc(apprenantId)).thenReturn(bookings);

        // When
        List<BookingDTO> result = bookingService.findByApprenantId(apprenantId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookingRepository).findByApprenantIdOrderByDateTimeDesc(apprenantId);
    }

    @Test
    void testFindByStatus() {
        // Given
        String status = "PENDING";
        Booking booking = createBooking(1L, LocalDateTime.now().plusDays(1), "PENDING");
        List<Booking> bookings = Arrays.asList(booking);
        
        when(bookingRepository.findByStatusOrderByDateTimeDesc(status)).thenReturn(bookings);

        // When
        List<BookingDTO> result = bookingService.findByStatus(status);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("PENDING", result.get(0).getStatus());
        verify(bookingRepository).findByStatusOrderByDateTimeDesc(status);
    }

    @Test
    void testCreateBooking() {
        // Given
        Mentor mentor = createMentor(1L, "John Doe", "john@example.com");
        Apprenant apprenant = createApprenant(1L, "Jane Smith", "jane@example.com");
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setMentorId(1L);
        bookingDTO.setApprenantId(1L);
        bookingDTO.setDateTime(dateTime);
        bookingDTO.setNotes("Test session");
        
        when(mentorRepository.findById(1L)).thenReturn(Optional.of(mentor));
        when(apprenantRepository.findById(1L)).thenReturn(Optional.of(apprenant));
        when(bookingRepository.findConflictingBookings(any(), any(), any())).thenReturn(Arrays.asList());
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> {
            Booking booking = invocation.getArgument(0);
            booking.setId(1L);
            return booking;
        });

        // When
        BookingDTO result = bookingService.createBooking(bookingDTO);

        // Then
        assertNotNull(result);
        assertEquals("PENDING", result.getStatus());
        assertEquals(1L, result.getMentorId());
        assertEquals(1L, result.getApprenantId());
        verify(mentorRepository).findById(1L);
        verify(apprenantRepository).findById(1L);
        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    void testCreateBookingWithConflict() {
        // Given
        Mentor mentor = createMentor(1L, "John Doe", "john@example.com");
        Apprenant apprenant = createApprenant(1L, "Jane Smith", "jane@example.com");
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setMentorId(1L);
        bookingDTO.setApprenantId(1L);
        bookingDTO.setDateTime(dateTime);
        
        Booking conflictingBooking = createBooking(2L, dateTime, "CONFIRMED");
        
        when(mentorRepository.findById(1L)).thenReturn(Optional.of(mentor));
        when(apprenantRepository.findById(1L)).thenReturn(Optional.of(apprenant));
        when(bookingRepository.findConflictingBookings(any(), any(), any())).thenReturn(Arrays.asList(conflictingBooking));

        // When & Then
        assertThrows(RuntimeException.class, () -> bookingService.createBooking(bookingDTO));
        verify(mentorRepository).findById(1L);
        verify(apprenantRepository).findById(1L);
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    void testUpdateBookingStatus() {
        // Given
        Long id = 1L;
        String newStatus = "CONFIRMED";
        Booking booking = createBooking(id, LocalDateTime.now().plusDays(1), "PENDING");
        
        when(bookingRepository.findById(id)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        // When
        BookingDTO result = bookingService.updateBookingStatus(id, newStatus);

        // Then
        assertNotNull(result);
        assertEquals("CONFIRMED", result.getStatus());
        verify(bookingRepository).findById(id);
        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    void testDeleteBooking() {
        // Given
        Long id = 1L;
        when(bookingRepository.existsById(id)).thenReturn(true);

        // When
        bookingService.deleteBooking(id);

        // Then
        verify(bookingRepository).existsById(id);
        verify(bookingRepository).deleteById(id);
    }

    @Test
    void testCountBookingsByMentor() {
        // Given
        Long mentorId = 1L;
        when(bookingRepository.countByMentorId(mentorId)).thenReturn(5L);

        // When
        long result = bookingService.countBookingsByMentor(mentorId);

        // Then
        assertEquals(5L, result);
        verify(bookingRepository).countByMentorId(mentorId);
    }

    private Booking createBooking(Long id, LocalDateTime dateTime, String status) {
        Booking booking = new Booking();
        booking.setId(id);
        booking.setDateTime(dateTime);
        booking.setStatus(status);
        booking.setCreatedAt(LocalDateTime.now());
        return booking;
    }

    private Mentor createMentor(Long id, String nom, String email) {
        Mentor mentor = new Mentor();
        mentor.setId(id);
        mentor.setNom(nom);
        mentor.setEmail(email);
        mentor.setRole("MENTOR");
        return mentor;
    }

    private Apprenant createApprenant(Long id, String nom, String email) {
        Apprenant apprenant = new Apprenant();
        apprenant.setId(id);
        apprenant.setNom(nom);
        apprenant.setEmail(email);
        apprenant.setRole("APPRENANT");
        return apprenant;
    }
}
