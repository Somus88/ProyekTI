package com.group3.healthconsult.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.group3.healthconsult.dto.UserDto;
import com.group3.healthconsult.models.User;
import com.group3.healthconsult.repository.UserRepository;
import com.group3.healthconsult.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> mapToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> mapToUserDto(value));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user;
    }

    @Override
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User update(UserDto userDto) {
        User user = mapToUser(userDto);
        return userRepository.save(user);
    }

    @Override
    public User delete(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
        }
        return user.orElse(null);
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = UserDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .role(user.getRole())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();
        
        return userDto;
    }

    private User mapToUser(UserDto userDto) {
        User user = User.builder()
            .id(userDto.getId())
            .username(userDto.getUsername())
            .password(userDto.getPassword())
            .role(userDto.getRole())
            .createdAt(userDto.getCreatedAt())
            .updatedAt(userDto.getUpdatedAt())
            .build();
        return user;
    }
}
