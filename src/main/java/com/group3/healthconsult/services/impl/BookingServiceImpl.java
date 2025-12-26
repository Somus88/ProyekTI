package com.group3.healthconsult.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group3.healthconsult.models.Booking;
import com.group3.healthconsult.models.Doctor;
import com.group3.healthconsult.models.Patient;
import com.group3.healthconsult.repository.BookingRepository;
import com.group3.healthconsult.repository.DoctorRepository;
import com.group3.healthconsult.services.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

        @Autowired
        private BookingRepository bookingRepository;

        @Autowired
        private DoctorRepository doctorRepository;

        @Override
        public Booking createBooking(Long doctorId, String patientName) {
                Doctor doctor = doctorRepository.findById(doctorId)
                                .orElseThrow(() -> new RuntimeException("Doctor not found"));

                LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
                LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

                Integer currentQueue = bookingRepository.countByDoctorIdAndCreatedAtBetween(doctor.getId(), startOfDay,
                                endOfDay);
                Integer nextQueueNumber = currentQueue + 1;

                Booking booking = Booking.builder()
                                .doctor(doctor)
                                .patientName(patientName)
                                .queueNumber(nextQueueNumber)
                                .status(com.group3.healthconsult.models.BookingStatus.WAITING)
                                .build();

                return bookingRepository.save(booking);
        }

        @Override
        public Booking createBooking(Patient patient, Doctor doctor) {
                LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
                LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

                Integer currentQueue = bookingRepository.countByDoctorIdAndCreatedAtBetween(doctor.getId(), startOfDay,
                                endOfDay);
                Integer nextQueueNumber = currentQueue + 1;

                Booking booking = Booking.builder()
                                .doctor(doctor)
                                .patient(patient)
                                .patientName(patient.getName())
                                .queueNumber(nextQueueNumber)
                                .status(com.group3.healthconsult.models.BookingStatus.WAITING)
                                .healthIssue(patient.getHealthIssue())
                                .build();

                return bookingRepository.save(booking);
        }

        @Override
        public List<Booking> getBookingsForDoctor(Doctor doctor) {
                return bookingRepository.findByDoctorOrderByQueueNumberAsc(doctor);
        }

        @Override
        public List<Booking> getTodayBookingsForDoctor(Doctor doctor) {
                LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
                LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
                return bookingRepository.findByDoctorAndCreatedAtBetweenOrderByQueueNumberAsc(doctor, startOfDay, endOfDay);
        }

        @Override
        public List<Booking> getPastBookingsForDoctor(Doctor doctor) {
                LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
                return bookingRepository.findByDoctorAndCreatedAtBeforeOrderByCreatedAtDesc(doctor, startOfDay);
        }

        @Override
        public void updateStatus(Long bookingId, com.group3.healthconsult.models.BookingStatus status) {
                Booking booking = bookingRepository.findById(bookingId)
                                .orElseThrow(() -> new RuntimeException("Booking not found"));
                booking.setStatus(status);
                bookingRepository.save(booking);
        }

        @Override
        public Booking getBookingById(Long id) {
                return bookingRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Booking not found"));
        }
}
