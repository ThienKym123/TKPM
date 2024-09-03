package com.example.project.controller;

import com.example.project.model.Teacher;
import com.example.project.service.TeacherService;
import com.example.project.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable("id") String teacherId) {
        Optional<Teacher> teacher = teacherService.getTeacherById(teacherId);
        return teacher.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<String> createTeacher(@RequestBody Teacher teacher) {
        try {
            Teacher createdTeacher = teacherService.saveTeacher(teacher);
            return new ResponseEntity<>("Giáo viên đã được thêm thành công.", HttpStatus.CREATED);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body("Lỗi khi thêm giáo viên: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTeacher(@PathVariable("id") String teacherId, @RequestBody Teacher teacher) {
        if (!teacherService.getTeacherById(teacherId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            teacher.setTeacherId(teacherId);
            Teacher updatedTeacher = teacherService.saveTeacher(teacher);
            return new ResponseEntity<>("Giáo viên đã được cập nhật thành công.", HttpStatus.OK);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật giáo viên: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable("id") String teacherId) {
        if (!teacherService.getTeacherById(teacherId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            teacherService.deleteTeacher(teacherId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi xóa giáo viên: " + e.getMessage());
        }
    }
}
