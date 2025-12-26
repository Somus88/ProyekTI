package com.group3.healthconsult.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group3.healthconsult.models.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {
}
