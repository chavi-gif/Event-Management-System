package com.example.event.Service;

import com.example.event.Model.User;
import com.example.event.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Login logic
    public User login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // simple password check (later improve with encryption)
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    // Add this registration method
    public boolean register(User user) {
        try {
            // Check if email already exists
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                return false;
            }

            // Check if student ID already exists
            if (user.getStudentId() != null && !user.getStudentId().trim().isEmpty()) {
                if (userRepository.findByStudentId(user.getStudentId()).isPresent()) {
                    return false;
                }
            }

            // Clean up empty student ID
            if (user.getStudentId() != null && user.getStudentId().trim().isEmpty()) {
                user.setStudentId(null);
            }

            // Save the user
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
