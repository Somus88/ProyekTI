package com.group3.healthconsult.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.group3.healthconsult.models.Booking;
import com.group3.healthconsult.models.Doctor;
import com.group3.healthconsult.services.BookingService;
import com.group3.healthconsult.services.DoctorService;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private DoctorService doctorService;

    // Restricted to authenticated users (or handled by AuthMiddleware)
    @GetMapping("/new")
    public String newBookingForm(@RequestParam("doctorId") Long doctorId, Model model) {
        // This route might be deprecated if only staff can book via add_patient
        return "redirect:/";
    }

    @PostMapping
    public String createBooking(@RequestParam("doctorId") Long doctorId,
            @RequestParam("patientName") String patientName,
            Model model) {
        // Deprecated or restricted
        return "redirect:/";
    }

    @GetMapping("/success")
    public String bookingSuccess(@RequestParam("queueNumber") Integer queueNumber,
            @RequestParam("doctorName") String doctorName,
            @RequestParam(value = "patientName", required = false) String patientName,
            @RequestParam(value = "healthIssue", required = false) String healthIssue,
            Model model) {
        model.addAttribute("queueNumber", queueNumber);
        model.addAttribute("doctorName", doctorName);
        model.addAttribute("patientName", patientName);
        model.addAttribute("healthIssue", healthIssue);
        return "bookings/success";
    }
}
