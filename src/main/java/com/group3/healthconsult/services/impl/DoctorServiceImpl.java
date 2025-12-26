package com.group3.healthconsult.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.group3.healthconsult.dto.DoctorDto;
import com.group3.healthconsult.models.Doctor;
import com.group3.healthconsult.models.User;
import com.group3.healthconsult.repository.DoctorRepository;
import com.group3.healthconsult.services.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {
    private DoctorRepository doctorRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public List<DoctorDto> getAll(
            @RequestParam(required = false) Long specialization) {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream().filter(doctor -> {
            Boolean isTrue = false;

            if (specialization == null) {
                isTrue = true;
            } else {
                if (specialization != null) {
                    isTrue = isTrue || doctor.getSpecialization().getId() == specialization;
                }
            }

            return isTrue;
        }).map(doctor -> mapToDoctorDto(doctor)).collect(Collectors.toList());
    }

    @Override
    public Optional<DoctorDto> findById(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        return doctor.map(value -> mapToDoctorDto(value));
    }

    @Override
    public Doctor create(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor update(DoctorDto doctorDto) {
        Doctor doctor = mapToDoctor(doctorDto);
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor delete(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (doctor.isPresent()) {
            doctorRepository.deleteById(id);
        }
        return doctor.orElse(null);
    }

    private DoctorDto mapToDoctorDto(Doctor doctor) {
        DoctorDto doctorDto = DoctorDto.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .specialization(doctor.getSpecialization())
                .medicalLicenseId(doctor.getMedicalLicenseId())
                .scheduleStart(doctor.getScheduleStart())
                .scheduleEnd(doctor.getScheduleEnd())
                .createdAt(doctor.getCreatedAt())
                .updatedAt(doctor.getUpdatedAt())
                .build();

        return doctorDto;
    }

    private Doctor mapToDoctor(DoctorDto doctorDto) {
        Doctor doctor = Doctor.builder()
                .id(doctorDto.getId())
                .name(doctorDto.getName())
                .specialization(doctorDto.getSpecialization())
                .medicalLicenseId(doctorDto.getMedicalLicenseId())
                .scheduleStart(doctorDto.getScheduleStart())
                .scheduleEnd(doctorDto.getScheduleEnd())
                .createdAt(doctorDto.getCreatedAt())
                .updatedAt(doctorDto.getUpdatedAt())
                .build();

        return doctor;
    }
}
