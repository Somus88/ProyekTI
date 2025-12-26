package com.group3.healthconsult.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpecializationDto {
    private Long id;

    @NotEmpty(message = "Specialization Name is required")
    private String name;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
