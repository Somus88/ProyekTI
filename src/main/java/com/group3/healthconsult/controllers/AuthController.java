package com.group3.healthconsult.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.group3.healthconsult.core.SecurityUtil;
import com.group3.healthconsult.dto.SpecializationDto;
import com.group3.healthconsult.models.Doctor;
import com.group3.healthconsult.models.Patient;
import com.group3.healthconsult.models.User;
import com.group3.healthconsult.services.DoctorService;
import com.group3.healthconsult.services.PatientService;
import com.group3.healthconsult.services.SpecializationService;
import com.group3.healthconsult.services.UserService;
import com.group3.healthconsult.services.StaffService;
import com.group3.healthconsult.models.Staff;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private StaffService staffService;
    private UserService userService;

    public AuthController(
            UserService userService,
            StaffService staffService) {
        this.userService = userService;
        this.staffService = staffService;
    }

    @GetMapping("/login")
    public String login() {
        String authenticatedUsername = SecurityUtil.getSessionUser();
        Optional<User> authenticatedUser = userService.findByUsername(authenticatedUsername);

        if (authenticatedUser.isPresent()) {
            return "redirect:/";
        }

        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            Model model) {
        Optional<User> existingUserOptional = userService.findByUsername(user.getUsername());

        if (existingUserOptional.isPresent()) {
            result.rejectValue("username", "error.user", "Username is already taken");
            return "redirect:/register?fail=true";
        }

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "redirect:/register?fail=true";
        }

        user.setRole("staff");
        userService.create(user);

        Staff staff = new Staff();
        staff.setUser(user);
        staffService.create(staff);

        return "redirect:/login?registerSuccess=true";
    }
}
