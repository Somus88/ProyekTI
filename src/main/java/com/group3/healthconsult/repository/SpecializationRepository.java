package com.group3.healthconsult.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group3.healthconsult.models.Specialization;
import java.util.Optional;


public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
    Optional<Specialization> findById(Long id);
    Optional<Specialization> findByName(String name);
}
