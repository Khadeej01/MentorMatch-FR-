package com.mentormatch.mapper;

import com.mentormatch.dto.BookingDTO;
import com.mentormatch.model.Booking;

public class BookingMapper {
    
    public static BookingDTO toDTO(Booking booking) {
        if (booking == null) {
            return null;
        }
        
        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setDateTime(booking.getDateTime());
        dto.setStatus(booking.getStatus());
        dto.setNotes(booking.getNotes());
        dto.setCreatedAt(booking.getCreatedAt());
        dto.setUpdatedAt(booking.getUpdatedAt());
        
        if (booking.getMentor() != null) {
            dto.setMentorId(booking.getMentor().getId());
            dto.setMentorName(booking.getMentor().getNom());
        }
        
        if (booking.getApprenant() != null) {
            dto.setApprenantId(booking.getApprenant().getId());
            dto.setApprenantName(booking.getApprenant().getNom());
        }
        
        return dto;
    }
    
    public static Booking toEntity(BookingDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Booking booking = new Booking();
        booking.setId(dto.getId());
        booking.setDateTime(dto.getDateTime());
        booking.setStatus(dto.getStatus());
        booking.setNotes(dto.getNotes());
        booking.setCreatedAt(dto.getCreatedAt());
        booking.setUpdatedAt(dto.getUpdatedAt());
        
        return booking;
    }
}
