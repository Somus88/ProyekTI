package com.group3.healthconsult.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group3.healthconsult.models.Patient;
import com.group3.healthconsult.models.User;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findById(Long id);

    java.util.List<Patient> findByNameContainingOrIdNumberContaining(String name, String idNumber);
}
