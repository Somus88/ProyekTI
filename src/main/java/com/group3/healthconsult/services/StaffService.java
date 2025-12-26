package com.group3.healthconsult.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group3.healthconsult.models.Staff;
import com.group3.healthconsult.repository.StaffRepository;

@Service
public class StaffService {
    private StaffRepository staffRepository;

    @Autowired
    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public Staff create(Staff staff) {
        return staffRepository.save(staff);
    }
}
