package com.group3.healthconsult.services;

import java.util.List;
import java.util.Optional;

import com.group3.healthconsult.dto.UserDto;
import com.group3.healthconsult.models.User;

public interface UserService {
    List<UserDto> getAll();
    Optional<UserDto> findById(Long id);
    Optional<User> findByUsername(String username);
    User create(User user);
    User update(UserDto user);
    User delete(Long id);
}
