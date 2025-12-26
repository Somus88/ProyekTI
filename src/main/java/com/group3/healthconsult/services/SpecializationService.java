package com.group3.healthconsult.services;

import java.util.List;
import java.util.Optional;

import com.group3.healthconsult.dto.SpecializationDto;
import com.group3.healthconsult.models.Specialization;

public interface SpecializationService {
    List<SpecializationDto> getAll();
    Optional<SpecializationDto> findById(Long id);
    Specialization create(Specialization specialization);
    Specialization update(SpecializationDto specialization);
    Specialization delete(Long id);
}
