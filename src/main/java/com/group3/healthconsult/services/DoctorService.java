package com.group3.healthconsult.services;

import java.util.List;
import java.util.Optional;

import com.group3.healthconsult.dto.DoctorDto;
import com.group3.healthconsult.models.Doctor;
import com.group3.healthconsult.models.User;

public interface DoctorService {
    List<DoctorDto> getAll(
            Long specialization);

    Optional<DoctorDto> findById(Long id);

    Doctor create(Doctor doctor);

    Doctor update(DoctorDto doctor);

    Doctor delete(Long id);
}
