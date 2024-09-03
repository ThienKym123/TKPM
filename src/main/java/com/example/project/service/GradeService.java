package com.example.project.service;

import com.example.project.exception.ValidationException;
import com.example.project.model.Grade;
import com.example.project.repository.GradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;

    public GradeService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    public Optional<Grade> getGradeById(String gradeId) {
        return gradeRepository.findById(gradeId);
    }

    public Grade saveGrade(Grade grade) {
        validateGrade(grade);
        return gradeRepository.save(grade);
    }

    public void deleteGrade(String gradeId) {
        gradeRepository.deleteById(gradeId);
    }

    private void validateGrade(Grade grade) {
        if (grade.getAssignmentId() == null || grade.getAssignmentId().isEmpty()) {
            throw new ValidationException("ID phân công giảng dạy không được để trống.");
        }
        // Additional validation logic can be added here
    }
}
