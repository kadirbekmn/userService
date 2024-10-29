package com.moonlight.userservice.service;

import com.moonlight.userservice.model.User;
import com.moonlight.userservice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        User actualUser = user.get();
        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(actualUser.getUsername());
        builder.password(actualUser.getPassword());
        builder.roles(actualUser.getRole());
        return builder.build();
    }
}
