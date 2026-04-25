package com.example.event.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "level_ranges")
public class LevelRange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String level;

    @Column(name = "academic_year", nullable = false)  // Changed from "year" to "academic_year"
    private String academicYear;

    @Column(name = "start_number", nullable = false)
    private Integer startNumber;

    @Column(name = "end_number", nullable = false)
    private Integer endNumber;

    public LevelRange() {}

    public LevelRange(String level, String academicYear, Integer startNumber, Integer endNumber) {
        this.level = level;
        this.academicYear = academicYear;
        this.startNumber = startNumber;
        this.endNumber = endNumber;
    }

    // Getters
    public Long getId() { return id; }
    public String getLevel() { return level; }
    public String getAcademicYear() { return academicYear; }
    public Integer getStartNumber() { return startNumber; }
    public Integer getEndNumber() { return endNumber; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setLevel(String level) { this.level = level; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
    public void setStartNumber(Integer startNumber) { this.startNumber = startNumber; }
    public void setEndNumber(Integer endNumber) { this.endNumber = endNumber; }
}