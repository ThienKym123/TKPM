package com.example.project.service;

import com.example.project.exception.ValidationException;
import com.example.project.model.Student;
import com.example.project.model.TeachingAssignment;
import com.example.project.repository.TeachingAssignmentRepository;
import com.example.project.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeachingAssignmentService {

    private final TeachingAssignmentRepository assignmentRepository;
    private final StudentRepository studentRepository;

    public TeachingAssignmentService(TeachingAssignmentRepository assignmentRepository, StudentRepository studentRepository) {
        this.assignmentRepository = assignmentRepository;
        this.studentRepository = studentRepository;
    }

    public List<TeachingAssignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public Optional<TeachingAssignment> getAssignmentById(String assignmentId) {
        return assignmentRepository.findById(assignmentId);
    }

    public TeachingAssignment saveAssignment(TeachingAssignment assignment) {
        validateAssignment(assignment);
        return assignmentRepository.save(assignment);
    }

    public void deleteAssignment(String assignmentId) {
        assignmentRepository.deleteById(assignmentId);
    }

    public void addAssignmentsForClass(String className, String teacherId, String subjectId) {
        List<Student> students = studentRepository.findByClassName(className);
        for (Student student : students) {
            TeachingAssignment assignment = new TeachingAssignment();
            assignment.setTeacherId(teacherId);
            assignment.setSubjectId(subjectId);
            assignment.setStudentId(student.getStudentId());
            saveAssignment(assignment);
        }
    }

    public List<TeachingAssignment> getAssignmentsWithoutGrades() {
        return assignmentRepository.findAssignmentsWithoutGrades();
    }

    private void validateAssignment(TeachingAssignment assignment) {
        if (assignment.getTeacherId() == null || assignment.getTeacherId().isEmpty()) {
            throw new ValidationException("ID giáo viên không được để trống.");
        }
        if (assignment.getSubjectId() == null || assignment.getSubjectId().isEmpty()) {
            throw new ValidationException("ID môn học không được để trống.");
        }
        if (assignment.getStudentId() == null || assignment.getStudentId().isEmpty()) {
            throw new ValidationException("ID học sinh không được để trống.");
        }

        List<TeachingAssignment> existingAssignments = assignmentRepository.findByTeacherIdAndSubjectIdAndStudentId(
                assignment.getTeacherId(), assignment.getSubjectId(), assignment.getStudentId());

        if (!existingAssignments.isEmpty()) {
            throw new ValidationException("Phân công giảng dạy đã tồn tại");
        }
    }
}
