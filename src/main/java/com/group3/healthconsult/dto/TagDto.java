package com.group3.healthconsult.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagDto {
    private Long id;
    
    @NotEmpty(message = "Tag name is required")
    private String name;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
