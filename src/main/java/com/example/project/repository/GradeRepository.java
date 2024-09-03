package com.example.project.repository;

import com.example.project.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, String> {
    // Custom queries can be added here if needed
}
