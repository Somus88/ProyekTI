package com.group3.healthconsult.services;

import java.util.List;

import com.group3.healthconsult.models.Booking;
import com.group3.healthconsult.models.BookingStatus;
import com.group3.healthconsult.models.Doctor;
import com.group3.healthconsult.models.Patient;

public interface BookingService {
    Booking createBooking(Long doctorId, String patientName);

    Booking createBooking(Patient patient, Doctor doctor);

    List<Booking> getBookingsForDoctor(Doctor doctor);

    List<Booking> getTodayBookingsForDoctor(Doctor doctor);

    List<Booking> getPastBookingsForDoctor(Doctor doctor);

    void updateStatus(Long bookingId, BookingStatus status);

    Booking getBookingById(Long id);
}
