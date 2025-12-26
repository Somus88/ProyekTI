package com.group3.healthconsult.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group3.healthconsult.dto.SpecializationDto;
import com.group3.healthconsult.models.Specialization;
import com.group3.healthconsult.repository.SpecializationRepository;
import com.group3.healthconsult.services.SpecializationService;

@Service
public class SpecializationServiceImpl implements SpecializationService {
    private SpecializationRepository specializationRepository;

    @Autowired
    public SpecializationServiceImpl(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }

    @Override
    public List<SpecializationDto> getAll() {
        List<Specialization> specializations = specializationRepository.findAll();
        return specializations.stream().map(specialization -> mapToSpecializationDto(specialization)).collect(Collectors.toList());
    }

    @Override
    public Optional<SpecializationDto> findById(Long id) {
        Optional<Specialization> specialization = specializationRepository.findById(id);
        return specialization.map(value -> mapToSpecializationDto(value));
    }

    @Override
    public Specialization create(Specialization specialization) {
        return specializationRepository.save(specialization);
    }

    @Override
    public Specialization update(SpecializationDto specializationDto) {
        Specialization specialization = mapToSpecialization(specializationDto);
        return specializationRepository.save(specialization);
    }

    @Override
    public Specialization delete(Long id) {
        Optional<Specialization> specialization = specializationRepository.findById(id);
        if (specialization.isPresent()) {
            specializationRepository.deleteById(id);
        }
        return specialization.orElse(null);
    }

    private SpecializationDto mapToSpecializationDto(Specialization specialization) {
        SpecializationDto specializationDto = SpecializationDto.builder()
            .id(specialization.getId())
            .name(specialization.getName())
            .createdAt(specialization.getCreatedAt())
            .updatedAt(specialization.getUpdatedAt())
            .build();
        
            return specializationDto;
    }

    private Specialization mapToSpecialization(SpecializationDto specializationDto) {
        Specialization specialization = Specialization.builder()
                .id(specializationDto.getId())
                .name(specializationDto.getName())
                .createdAt(specializationDto.getCreatedAt())
                .updatedAt(specializationDto.getUpdatedAt())
                .build();

        return specialization;
    }
}
