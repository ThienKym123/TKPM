package com.example.project.service;

import com.example.project.exception.ValidationException;
import com.example.project.model.Teacher;
import com.example.project.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> getTeacherById(String teacherId) {
        return teacherRepository.findById(teacherId);
    }

    public Teacher saveTeacher(Teacher teacher) {
        validateTeacher(teacher);
        return teacherRepository.save(teacher);
    }

    public void deleteTeacher(String teacherId) {
        teacherRepository.deleteById(teacherId);
    }

    private void validateTeacher(Teacher teacher) {
        if (teacher.getName() == null || teacher.getName().isEmpty()) {
            throw new ValidationException("Tên giáo viên không được để trống.");
        }
        if (teacher.getBirthday() == null || teacher.getBirthday().isEmpty()) {
            throw new ValidationException("Ngày sinh không được để trống.");
        }
        if (teacher.getSubjectName() == null || teacher.getSubjectName().isEmpty()) {
            throw new ValidationException("Tên môn học giảng dạy không được để trống.");
        }
        if (teacher.getAccountId() == null || teacher.getAccountId().isEmpty()) {
            throw new ValidationException("ID tài khoản không được để trống.");
        }
    }
}
