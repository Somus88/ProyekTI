package com.group3.healthconsult.core;

import com.group3.healthconsult.repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MainUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public MainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.group3.healthconsult.models.User> user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        User authenticatedUser = new User(
            user.get().getUsername(),
            user.get().getPassword(),
            AuthorityUtils.createAuthorityList(user.get().getRole())
        );

        return authenticatedUser;
    }
}