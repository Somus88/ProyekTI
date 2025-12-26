package com.group3.healthconsult.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group3.healthconsult.models.Booking;
import com.group3.healthconsult.models.Doctor;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Integer countByDoctorIdAndCreatedAtBetween(Long doctorId, LocalDateTime start, LocalDateTime end);

    List<Booking> findByDoctorOrderByQueueNumberAsc(Doctor doctor);

    List<Booking> findByDoctorAndCreatedAtBetweenOrderByQueueNumberAsc(Doctor doctor, LocalDateTime start,
            LocalDateTime end);

    List<Booking> findByDoctorAndCreatedAtBeforeOrderByCreatedAtDesc(Doctor doctor, LocalDateTime date);
}
