package com.group3.healthconsult.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.group3.healthconsult.dto.DoctorDto;
import com.group3.healthconsult.dto.PatientDto;
import com.group3.healthconsult.models.Doctor;
import com.group3.healthconsult.models.Patient;
import com.group3.healthconsult.services.DoctorService;
import com.group3.healthconsult.services.PatientService;
import com.group3.healthconsult.services.SpecializationService;
import com.group3.healthconsult.services.UserService;
import com.group3.healthconsult.models.User;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/staff")
public class StaffController {
    private DoctorService doctorService;
    private PatientService patientService;
    private SpecializationService specializationService;

    @Autowired
    private UserService userService;

    @Autowired
    private com.group3.healthconsult.services.BookingService bookingService;

    @Autowired
    private com.group3.healthconsult.repository.DoctorRepository doctorRepository;

    @Autowired
    private com.group3.healthconsult.repository.PatientRepository patientRepository;

    @Autowired
    public StaffController(DoctorService doctorService, PatientService patientService,
            SpecializationService specializationService, UserService userService,
            com.group3.healthconsult.repository.PatientRepository patientRepository) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.specializationService = specializationService;
        this.userService = userService;
        this.patientRepository = patientRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "redirect:/";
    }

    @GetMapping("/doctors/new")
    public String addDoctorForm(Model model) {
        model.addAttribute("doctor", new DoctorDto());
        model.addAttribute("specializations", specializationService.getAll());
        return "staff/add_doctor";
    }

    @PostMapping("/doctors")
    public String addDoctor(@Valid @ModelAttribute("doctor") DoctorDto doctorDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("specializations", specializationService.getAll());
            return "staff/add_doctor";
        }

        // Create User
        User user = new User();
        user.setUsername(doctorDto.getUsername());
        user.setPassword(doctorDto.getPassword());
        user.setRole("doctor");
        User savedUser = userService.create(user);

        // Create Doctor linked to User
        Doctor doctor = new Doctor();
        doctor.setName(doctorDto.getName());
        doctor.setSpecialization(doctorDto.getSpecialization());
        doctor.setMedicalLicenseId(doctorDto.getMedicalLicenseId());
        doctor.setScheduleStart(doctorDto.getScheduleStart());
        doctor.setScheduleEnd(doctorDto.getScheduleEnd());
        doctor.setUser(savedUser);

        doctorService.create(doctor);

        return "redirect:/?success=true";
    }

    @GetMapping("/patients/new")
    public String addPatientForm(Model model) {
        model.addAttribute("patient", new PatientDto());
        model.addAttribute("doctors", doctorService.getAll(null));
        return "staff/add_patient";
    }

    @PostMapping("/patients")
    public String addPatient(@Valid @ModelAttribute("patient") PatientDto patientDto, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("doctors", doctorService.getAll(null));
            return "staff/add_patient";
        }

        // Create Patient
        Patient patient = new Patient();
        patient.setName(patientDto.getName());
        patient.setEmail(patientDto.getEmail());
        patient.setAge(patientDto.getAge());
        patient.setDob(patientDto.getDob());
        patient.setGender(patientDto.getGender());
        patient.setAddress(patientDto.getAddress());
        patient.setIdNumber(patientDto.getIdNumber());
        patient.setPhoneNumber(patientDto.getPhoneNumber());
        patient.setHealthIssue(patientDto.getHealthIssue());
        patient.setMedicationAllergies(patientDto.getMedicationAllergies());
        patient.setBpjsNumber(patientDto.getBpjsNumber());

        Patient savedPatient = patientService.create(patient);

        // Create Booking
        Doctor doctor = doctorRepository.findById(patientDto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        com.group3.healthconsult.models.Booking booking = bookingService.createBooking(savedPatient, doctor);

        return "redirect:/bookings/success?queueNumber=" + booking.getQueueNumber() + "&doctorName="
                + doctor.getName() + "&patientName=" + savedPatient.getName() + "&healthIssue="
                + savedPatient.getHealthIssue();
    }

    @GetMapping("/patients/search")
    public String searchPatients(
            @org.springframework.web.bind.annotation.RequestParam(value = "query", required = false) String query,
            Model model) {
        if (query != null && !query.isEmpty()) {
            model.addAttribute("patients",
                    patientRepository.findByNameContainingOrIdNumberContaining(query, query));
        }
        return "staff/search_patient";
    }

    @GetMapping("/patients/{id}/book")
    public String bookingFormForPatient(@org.springframework.web.bind.annotation.PathVariable("id") Long id,
            Model model) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        PatientDto patientDto = new PatientDto();
        // Pre-fill data
        patientDto.setName(patient.getName());
        patientDto.setEmail(patient.getEmail());
        patientDto.setAge(patient.getAge());
        patientDto.setDob(patient.getDob());
        patientDto.setGender(patient.getGender());
        patientDto.setAddress(patient.getAddress());
        patientDto.setIdNumber(patient.getIdNumber());
        patientDto.setPhoneNumber(patient.getPhoneNumber());
        // Allow editing these:
        patientDto.setHealthIssue(patient.getHealthIssue());
        patientDto.setMedicationAllergies(patient.getMedicationAllergies());
        patientDto.setBpjsNumber(patient.getBpjsNumber());

        model.addAttribute("patient", patientDto);
        model.addAttribute("patientId", id); // Keep track of original ID
        model.addAttribute("doctors", doctorService.getAll(null));
        return "staff/book_existing";
    }

    @PostMapping("/bookings/existing")
    public String createBookingForExistingPatient(
            @org.springframework.web.bind.annotation.RequestParam("patientId") Long patientId,
            @Valid @ModelAttribute("patient") PatientDto patientDto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("patientId", patientId);
            model.addAttribute("doctors", doctorService.getAll(null));
            return "staff/book_existing";
        }

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        // Update mutable fields
        patient.setHealthIssue(patientDto.getHealthIssue());
        patient.setMedicationAllergies(patientDto.getMedicationAllergies());
        patient.setBpjsNumber(patientDto.getBpjsNumber());
        patientService.create(patient); // Save updates

        Doctor doctor = doctorRepository.findById(patientDto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        com.group3.healthconsult.models.Booking booking = bookingService.createBooking(patient, doctor);

        return "redirect:/bookings/success?queueNumber=" + booking.getQueueNumber() + "&doctorName="
                + doctor.getName() + "&patientName=" + patient.getName() + "&healthIssue=" + patient.getHealthIssue();
    }
}
