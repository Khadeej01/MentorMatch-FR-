package com.mentormatch.mapper;

import com.mentormatch.dto.BookingDTO;
import com.mentormatch.model.Apprenant;
import com.mentormatch.model.Booking;
import com.mentormatch.model.Mentor;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingMapperTest {

    @Test
    void testToDTO() {
        // Given
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now().plusHours(1);
        
        Mentor mentor = new Mentor();
        mentor.setId(1L);
        mentor.setNom("John Doe");
        
        Apprenant apprenant = new Apprenant();
        apprenant.setId(2L);
        apprenant.setNom("Jane Smith");
        
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setDateTime(dateTime);
        booking.setStatus("PENDING");
        booking.setNotes("Test session");
        booking.setMentor(mentor);
        booking.setApprenant(apprenant);
        booking.setCreatedAt(createdAt);
        booking.setUpdatedAt(updatedAt);

        // When
        BookingDTO dto = BookingMapper.toDTO(booking);

        // Then
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals(dateTime, dto.getDateTime());
        assertEquals("PENDING", dto.getStatus());
        assertEquals("Test session", dto.getNotes());
        assertEquals(1L, dto.getMentorId());
        assertEquals("John Doe", dto.getMentorName());
        assertEquals(2L, dto.getApprenantId());
        assertEquals("Jane Smith", dto.getApprenantName());
        assertEquals(createdAt, dto.getCreatedAt());
        assertEquals(updatedAt, dto.getUpdatedAt());
    }

    @Test
    void testToDTOWithNull() {
        // When
        BookingDTO dto = BookingMapper.toDTO(null);

        // Then
        assertNull(dto);
    }

    @Test
    void testToDTOWithNullMentor() {
        // Given
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setDateTime(dateTime);
        booking.setStatus("PENDING");
        booking.setMentor(null);
        booking.setApprenant(null);

        // When
        BookingDTO dto = BookingMapper.toDTO(booking);

        // Then
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals(dateTime, dto.getDateTime());
        assertEquals("PENDING", dto.getStatus());
        assertNull(dto.getMentorId());
        assertNull(dto.getMentorName());
        assertNull(dto.getApprenantId());
        assertNull(dto.getApprenantName());
    }

    @Test
    void testToEntity() {
        // Given
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now().plusHours(1);
        
        BookingDTO dto = new BookingDTO();
        dto.setId(1L);
        dto.setDateTime(dateTime);
        dto.setStatus("PENDING");
        dto.setNotes("Test session");
        dto.setMentorId(1L);
        dto.setMentorName("John Doe");
        dto.setApprenantId(2L);
        dto.setApprenantName("Jane Smith");
        dto.setCreatedAt(createdAt);
        dto.setUpdatedAt(updatedAt);

        // When
        Booking booking = BookingMapper.toEntity(dto);

        // Then
        assertNotNull(booking);
        assertEquals(1L, booking.getId());
        assertEquals(dateTime, booking.getDateTime());
        assertEquals("PENDING", booking.getStatus());
        assertEquals("Test session", booking.getNotes());
        assertEquals(createdAt, booking.getCreatedAt());
        assertEquals(updatedAt, booking.getUpdatedAt());
        // Note: mentor and apprenant are not set in toEntity, only IDs are preserved
    }

    @Test
    void testToEntityWithNull() {
        // When
        Booking booking = BookingMapper.toEntity(null);

        // Then
        assertNull(booking);
    }

    @Test
    void testRoundTripConversion() {
        // Given
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        LocalDateTime createdAt = LocalDateTime.now();
        
        Booking originalBooking = new Booking();
        originalBooking.setId(1L);
        originalBooking.setDateTime(dateTime);
        originalBooking.setStatus("PENDING");
        originalBooking.setNotes("Test session");
        originalBooking.setCreatedAt(createdAt);

        // When
        BookingDTO dto = BookingMapper.toDTO(originalBooking);
        Booking convertedBooking = BookingMapper.toEntity(dto);

        // Then
        assertNotNull(convertedBooking);
        assertEquals(originalBooking.getId(), convertedBooking.getId());
        assertEquals(originalBooking.getDateTime(), convertedBooking.getDateTime());
        assertEquals(originalBooking.getStatus(), convertedBooking.getStatus());
        assertEquals(originalBooking.getNotes(), convertedBooking.getNotes());
        assertEquals(originalBooking.getCreatedAt(), convertedBooking.getCreatedAt());
    }

    @Test
    void testToDTOWithMinimalData() {
        // Given
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        
        Booking booking = new Booking();
        booking.setId(2L);
        booking.setDateTime(dateTime);
        booking.setStatus("CONFIRMED");

        // When
        BookingDTO dto = BookingMapper.toDTO(booking);

        // Then
        assertNotNull(dto);
        assertEquals(2L, dto.getId());
        assertEquals(dateTime, dto.getDateTime());
        assertEquals("CONFIRMED", dto.getStatus());
        assertNull(dto.getNotes());
        assertNull(dto.getMentorId());
        assertNull(dto.getMentorName());
        assertNull(dto.getApprenantId());
        assertNull(dto.getApprenantName());
    }
}
