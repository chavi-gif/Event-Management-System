package com.example.event.Repository;

import com.example.event.Model.LevelRange;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LevelRangeRepository extends JpaRepository<LevelRange, Long> {
    Optional<LevelRange> findByLevelAndAcademicYear(String level, String academicYear);

    Optional<LevelRange> findByAcademicYear(String academicYear);
}
