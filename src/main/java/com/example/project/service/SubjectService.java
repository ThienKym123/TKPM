package com.example.project.service;

import com.example.project.exception.ValidationException;
import com.example.project.model.Subject;
import com.example.project.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Optional<Subject> getSubjectById(String subjectId) {
        return subjectRepository.findById(subjectId);
    }

    public Subject saveSubject(Subject subject) {
        validateSubject(subject);
        return subjectRepository.save(subject);
    }

    public void deleteSubject(String subjectId) {
        subjectRepository.deleteById(subjectId);
    }

    private void validateSubject(Subject subject) {
        if (subject.getSubjectName() == null || subject.getSubjectName().isEmpty()) {
            throw new ValidationException("Tên môn học không được để trống.");
        }
        if (subject.getSemester() <= 0) {
            throw new ValidationException("Học kỳ không hợp lệ.");
        }
        if (subject.getSchoolYear() == null || subject.getSchoolYear().isEmpty()) {
            throw new ValidationException("Năm học không được để trống.");
        }
    }
}
