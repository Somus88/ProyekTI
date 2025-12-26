package com.group3.healthconsult.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.group3.healthconsult.models.Specialization;
import com.group3.healthconsult.models.User;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {
    private Long id;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "Password is required")
    private String password;

    @NotNull(message = "Specialization is required")
    private Specialization specialization;

    @NotEmpty(message = "Medical License is required")
    private String medicalLicenseId;

    private LocalTime scheduleStart;
    private LocalTime scheduleEnd;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
