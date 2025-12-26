package com.group3.healthconsult.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.group3.healthconsult.dto.DoctorDto;
import com.group3.healthconsult.dto.SpecializationDto;
import com.group3.healthconsult.services.DoctorService;
import com.group3.healthconsult.services.SpecializationService;

@Controller
public class MainController {
    private SpecializationService specializationService;
    private DoctorService doctorService;

    @Autowired
    public MainController(
            SpecializationService specializationService,
            DoctorService doctorService) {
        this.specializationService = specializationService;
        this.doctorService = doctorService;
    }

    @GetMapping("/")
    public String index(
            Model model,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long specialization) {
        List<SpecializationDto> specializations = specializationService.getAll();
        List<DoctorDto> doctors = doctorService.getAll(specialization);

        model.addAttribute("specializations", specializations);
        model.addAttribute("doctors", doctors);
        return "index";
    }

}
