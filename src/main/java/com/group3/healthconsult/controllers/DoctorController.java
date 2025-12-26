package com.group3.healthconsult.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.group3.healthconsult.dto.DoctorDto;
import com.group3.healthconsult.models.BookingStatus;
import com.group3.healthconsult.models.Doctor;
import com.group3.healthconsult.services.BookingService;
import com.group3.healthconsult.repository.DoctorRepository;
import com.group3.healthconsult.services.DoctorService;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class DoctorController {
    private DoctorService doctorService;
    private BookingService bookingService;
    private DoctorRepository doctorRepository;

    @Autowired
    public DoctorController(DoctorService doctorService,
            BookingService bookingService, DoctorRepository doctorRepository) {
        this.doctorService = doctorService;
        this.bookingService = bookingService;
        this.doctorRepository = doctorRepository;
    }

    // GET (List)
    @GetMapping("/doctors")
    public String getAllDoctors(Model model) {
        List<DoctorDto> doctors = doctorService.getAll(null);
        model.addAttribute("doctors", doctors);
        return "redirect:/";
    }

    @GetMapping("/doctors/dashboard")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Doctor doctor = doctorRepository.findByUser_Username(username)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        model.addAttribute("bookings", bookingService.getTodayBookingsForDoctor(doctor));
        model.addAttribute("doctor", doctor);

        return "doctors/dashboard";
    }

    @GetMapping("/doctors/bookings/log")
    public String bookingLog(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Doctor doctor = doctorRepository.findByUser_Username(username)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        model.addAttribute("bookings", bookingService.getPastBookingsForDoctor(doctor));
        model.addAttribute("doctor", doctor);

        return "doctors/booking_log";
    }

    // POST
    @GetMapping("/doctors/{id}")
    public String getDoctorById(@PathVariable("id") Long id, Model model) {
        DoctorDto doctor = doctorService.findById(id).orElse(null);

        model.addAttribute("doctor", doctor);

        return "doctors/show";
    }

    // POST (Create)
    @PostMapping("/doctors")
    public String createNewDoctor(@Valid @ModelAttribute("doctor") Doctor doctor, BindingResult result) {
        if (result.hasErrors()) {
            return "doctors/create";
        }

        doctorService.create(doctor);
        return "redirect:/doctors";
    }

    @PostMapping("/doctors/bookings/{id}/status")
    public String updateBookingStatus(@PathVariable("id") Long id) {
        com.group3.healthconsult.models.Booking booking = bookingService.getBookingById(id);
        BookingStatus currentStatus = booking.getStatus();
        BookingStatus nextStatus;

        if (currentStatus == BookingStatus.WAITING) {
            nextStatus = BookingStatus.TREATING;
        } else if (currentStatus == BookingStatus.TREATING) {
            nextStatus = BookingStatus.FINISHED;
        } else {
            nextStatus = BookingStatus.WAITING;
        }

        bookingService.updateStatus(id, nextStatus);
        return "redirect:/doctors/dashboard";
    }
}
