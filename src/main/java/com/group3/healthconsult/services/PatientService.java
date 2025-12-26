package com.group3.healthconsult.services;

import java.util.List;
import java.util.Optional;

import com.group3.healthconsult.dto.PatientDto;
import com.group3.healthconsult.models.Patient;

public interface PatientService {
    List<PatientDto> getAll();

    Optional<PatientDto> findById(Long id);

    Patient create(Patient patient);

    Patient update(PatientDto patientDto);

    Patient delete(Long id);
}
