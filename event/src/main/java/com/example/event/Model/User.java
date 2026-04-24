package com.example.event.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "student_id", unique = true)
    private String studentId;

    private String level;

    @Column(name = "phone_number")
    private String phoneNumber;

    // Constructors
    public User() {}

    public User(String email, String password, String firstName, String lastName,
                String studentId, String level, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
        this.level = level;
        this.phoneNumber = phoneNumber;
    }

    // Getters
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getStudentId() { return studentId; }
    public String getLevel() { return level; }
    public String getPhoneNumber() { return phoneNumber; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setLevel(String level) { this.level = level; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
