package com.example.project.controller;

import com.example.project.model.Student;
import com.example.project.service.StudentService;
import com.example.project.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") String studentId) {
        Optional<Student> student = studentService.getStudentById(studentId);
        return student.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<String> createStudent(@RequestBody Student student) {
        try {
            studentService.saveStudent(student);
            return new ResponseEntity<>("Học sinh đã được thêm thành công.", HttpStatus.CREATED);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body("Lỗi khi thêm học sinh: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable("id") String studentId, @RequestBody Student student) {
        if (!studentService.getStudentById(studentId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            student.setStudentId(studentId);
            studentService.saveStudent(student);
            return new ResponseEntity<>("Học sinh đã được cập nhật thành công.", HttpStatus.OK);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật học sinh: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") String studentId) {
        if (!studentService.getStudentById(studentId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            studentService.deleteStudent(studentId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi xóa học sinh: " + e.getMessage());
        }
    }
}
