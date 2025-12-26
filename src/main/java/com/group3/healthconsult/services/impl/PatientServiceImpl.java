package com.group3.healthconsult.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group3.healthconsult.dto.PatientDto;
import com.group3.healthconsult.models.Patient;
import com.group3.healthconsult.repository.PatientRepository;
import com.group3.healthconsult.services.PatientService;

@Service
public class PatientServiceImpl implements PatientService {
    private PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<PatientDto> getAll() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(patient -> mapToPatientDto(patient)).collect(Collectors.toList());
    }

    @Override
    public Optional<PatientDto> findById(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        return patient.map(value -> mapToPatientDto(value));
    }

    @Override
    public Patient create(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient update(PatientDto patientDto) {
        Patient patient = mapToPatient(patientDto);
        return patientRepository.save(patient);
    }

    @Override
    public Patient delete(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            patientRepository.deleteById(id);
        }
        return patient.orElse(null);
    }

    private PatientDto mapToPatientDto(Patient patient) {
        PatientDto patientDto = PatientDto.builder()
                .id(patient.getId())
                .name(patient.getName())
                .createdAt(patient.getCreatedAt())
                .updatedAt(patient.getUpdatedAt())
                .build();

        return patientDto;
    }

    private Patient mapToPatient(PatientDto patientDto) {
        Patient patient = Patient.builder()
                .id(patientDto.getId())
                .name(patientDto.getName())
                .createdAt(patientDto.getCreatedAt())
                .updatedAt(patientDto.getUpdatedAt())
                .build();

        return patient;
    }
}
