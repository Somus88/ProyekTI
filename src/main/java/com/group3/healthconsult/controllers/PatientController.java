package com.group3.healthconsult.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.group3.healthconsult.dto.PatientDto;
import com.group3.healthconsult.services.PatientService;

@Controller
public class PatientController {
    @Autowired
    private PatientService patientService;

    // GET (List)
    @GetMapping("/patients")
    public String getAllPatients(Model model) {
        List<PatientDto> patients = patientService.getAll();
        model.addAttribute("patients", patients);
        return "redirect:/";
    }

    // GET
    @GetMapping("/patients/{id}")
    public String getPatientById(@PathVariable("id") Long id, Model model) {
        PatientDto patient = patientService.findById(id).orElse(null);
        model.addAttribute("patient", patient);
        return "patients/show";
    }
}
