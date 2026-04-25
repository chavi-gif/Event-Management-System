package com.example.event.Controller;

import com.example.event.Model.User;
import com.example.event.Model.LevelRange;
import com.example.event.Repository.LevelRangeRepository;
import com.example.event.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private LevelRangeRepository levelRangeRepository;

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();

        // Basic validation
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            response.put("success", false);
            response.put("message", "Email is required");
            return ResponseEntity.badRequest().body(response);
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            response.put("success", false);
            response.put("message", "Password is required");
            return ResponseEntity.badRequest().body(response);
        }

        if (user.getPassword().length() < 4) {
            response.put("success", false);
            response.put("message", "Password must be at least 4 characters");
            return ResponseEntity.badRequest().body(response);
        }

        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            response.put("success", false);
            response.put("message", "First name is required");
            return ResponseEntity.badRequest().body(response);
        }

        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            response.put("success", false);
            response.put("message", "Last name is required");
            return ResponseEntity.badRequest().body(response);
        }

        // Set default level if not provided
        if (user.getLevel() == null || user.getLevel().isEmpty()) {
            user.setLevel("1");
        }

        // Validate Student ID matches the selected level using database
        if (user.getStudentId() != null && !user.getStudentId().isEmpty()) {
            if (!validateStudentIdWithLevel(user.getStudentId(), user.getLevel())) {
                response.put("success", false);
                response.put("message", "Student ID does not match the selected level!");
                return ResponseEntity.badRequest().body(response);
            }
        }

        // Attempt registration
        boolean registered = userService.register(user);

        if (registered) {
            response.put("success", true);
            response.put("message", "Registration successful! Please login.");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Email or Student ID already exists!");
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Validation method using database
    private boolean validateStudentIdWithLevel(String studentId, String level) {
        if (studentId == null || studentId.isEmpty()) {
            return true;
        }

        try {
            String[] parts = studentId.split("/");
            if (parts.length != 3) return false;

            String year = parts[1];
            int number = Integer.parseInt(parts[2]);

            // Get range from database - FIXED METHOD NAME
            Optional<LevelRange> rangeOpt = levelRangeRepository.findByLevelAndAcademicYear(level, year);

            if (rangeOpt.isPresent()) {
                LevelRange range = rangeOpt.get();
                return number >= range.getStartNumber() && number <= range.getEndNumber();
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }
    @GetMapping("/api/detect-level")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> detectLevelFromStudentId(@RequestParam String studentId) {
        Map<String, Object> response = new HashMap<>();

        if (studentId == null || studentId.isEmpty()) {
            response.put("valid", true);
            return ResponseEntity.ok(response);
        }

        try {
            // Validate format
            String[] parts = studentId.split("/");
            if (parts.length != 3) {
                response.put("valid", false);
                response.put("message", "Invalid format. Use: TG/2024/1234");
                return ResponseEntity.badRequest().body(response);
            }

            String year = parts[1];
            int number = Integer.parseInt(parts[2]);

            // Find level range by year
            Optional<LevelRange> rangeOpt = levelRangeRepository.findByAcademicYear(year);

            if (rangeOpt.isPresent()) {
                LevelRange range = rangeOpt.get();
                if (number >= range.getStartNumber() && number <= range.getEndNumber()) {
                    response.put("valid", true);
                    response.put("level", range.getLevel());
                    response.put("message", "Student ID recognized! Level " + range.getLevel() + " selected.");
                    return ResponseEntity.ok(response);
                } else {
                    response.put("valid", false);
                    response.put("message", "Student ID number is out of range for year " + year);
                    return ResponseEntity.badRequest().body(response);
                }
            } else {
                response.put("valid", false);
                response.put("message", "No level found for year " + year);
                return ResponseEntity.badRequest().body(response);
            }

        } catch (Exception e) {
            response.put("valid", false);
            response.put("message", "Invalid Student ID format");
            return ResponseEntity.badRequest().body(response);
        }
    }
}