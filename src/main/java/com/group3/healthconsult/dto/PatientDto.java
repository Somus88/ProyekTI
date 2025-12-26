package com.group3.healthconsult.dto;

import java.time.LocalDateTime;

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
public class PatientDto {
    private Long id;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Email is required")
    private String email;

    @NotNull(message = "Age is required")
    private Integer age;

    @NotNull(message = "Date of Birth is required")
    private java.time.LocalDate dob;

    @NotEmpty(message = "Gender is required")
    private String gender;

    @NotEmpty(message = "Address is required")
    private String address;

    @NotEmpty(message = "ID Number is required")
    private String idNumber;

    @NotEmpty(message = "Phone Number is required")
    private String phoneNumber;

    @NotEmpty(message = "Health Issue is required")
    private String healthIssue;

    private String medicationAllergies;
    private String bpjsNumber;

    @NotNull(message = "Doctor is required")
    private Long doctorId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
