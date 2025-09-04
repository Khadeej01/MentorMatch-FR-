package com.mentormatch.dto;

import java.time.LocalDateTime;

public class BookingDTO {
    private Long id;
    private LocalDateTime dateTime;
    private String status;
    private String notes;
    private Long mentorId;
    private String mentorName;
    private Long apprenantId;
    private String apprenantName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public BookingDTO() {}

    public BookingDTO(Long id, LocalDateTime dateTime, String status, String notes, 
                     Long mentorId, String mentorName, Long apprenantId, String apprenantName,
                     LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.dateTime = dateTime;
        this.status = status;
        this.notes = notes;
        this.mentorId = mentorId;
        this.mentorName = mentorName;
        this.apprenantId = apprenantId;
        this.apprenantName = apprenantName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Long getMentorId() { return mentorId; }
    public void setMentorId(Long mentorId) { this.mentorId = mentorId; }

    public String getMentorName() { return mentorName; }
    public void setMentorName(String mentorName) { this.mentorName = mentorName; }

    public Long getApprenantId() { return apprenantId; }
    public void setApprenantId(Long apprenantId) { this.apprenantId = apprenantId; }

    public String getApprenantName() { return apprenantName; }
    public void setApprenantName(String apprenantName) { this.apprenantName = apprenantName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
