package com.moonlight.userservice.service;

import com.moonlight.userservice.model.User;
import com.moonlight.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            return userRepository.save(user);
        } else {
            throw new RuntimeException("Kullanıcı bulunamadı.");
        }
    }

    public void updatePassword(Long id, String oldPassword, String newPassword) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            // Eski şifreyi doğrula
            if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPassword));  // Şifreyi güncelle ve hashle
                userRepository.save(user);
            } else {
                throw new RuntimeException("Eski şifre hatalı.");
            }
        } else {
            throw new RuntimeException("Kullanıcı bulunamadı.");
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
