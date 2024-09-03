package com.example.project.service;

import com.example.project.exception.ValidationException;
import com.example.project.model.Student;
import com.example.project.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(String studentId) {
        return studentRepository.findById(studentId);
    }

    public List<Student> getStudentsByClassName(String className) {
        return studentRepository.findByClassName(className);
    }

    public Student saveStudent(Student student) {
        validateStudent(student);
        return studentRepository.save(student);
    }

    public void deleteStudent(String studentId) {
        studentRepository.deleteById(studentId);
    }

    private void validateStudent(Student student) {
        if (student.getName() == null || student.getName().isEmpty()) {
            throw new ValidationException("Tên học sinh không được để trống.");
        }
        if (student.getBirthday() == null) {
            throw new ValidationException("Ngày sinh không được để trống.");
        }
        if (student.getGender() == null || student.getGender().isEmpty()) {
            throw new ValidationException("Giới tính không được để trống.");
        }
        if (student.getSchoolYear() <= 0) {
            throw new ValidationException("Năm học không hợp lệ.");
        }
    }
}
