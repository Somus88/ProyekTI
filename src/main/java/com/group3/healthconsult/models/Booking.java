package com.group3.healthconsult.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    // Storing patient name directly for guest bookings
    private String patientName;

    // Optional: Link to Patient entity if we want to support registered patients
    // later
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = true)
    private Patient patient;

    private Integer queueNumber;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @jakarta.persistence.Enumerated(jakarta.persistence.EnumType.STRING)
    @Builder.Default
    private BookingStatus status = BookingStatus.WAITING;

    private String healthIssue;
}
