package com.group3.healthconsult.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import com.group3.healthconsult.models.Doctor;
import com.group3.healthconsult.models.Specialization;
import com.group3.healthconsult.models.User;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findById(Long id);

    Optional<Doctor> findByUser_Username(String username);

    List<Doctor> findByMedicalLicenseId(String medicalLicenseId);

    List<Doctor> findBySpecialization(Specialization specialization);

}
