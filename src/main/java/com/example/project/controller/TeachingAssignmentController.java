package com.example.project.controller;

import com.example.project.exception.ValidationException;
import com.example.project.model.TeachingAssignment;
import com.example.project.service.TeachingAssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teaching-assignments")
public class TeachingAssignmentController {

    private final TeachingAssignmentService teachingAssignmentService;

    public TeachingAssignmentController(TeachingAssignmentService teachingAssignmentService) {
        this.teachingAssignmentService = teachingAssignmentService;
    }

    @GetMapping
    public ResponseEntity<List<TeachingAssignment>> getAllAssignments() {
        List<TeachingAssignment> assignments = teachingAssignmentService.getAllAssignments();
        return new ResponseEntity<>(assignments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeachingAssignment> getAssignmentById(@PathVariable("id") String assignmentId) {
        return teachingAssignmentService.getAssignmentById(assignmentId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<String> createAssignment(@RequestBody TeachingAssignment assignment) {
        try {
            teachingAssignmentService.saveAssignment(assignment);
            return new ResponseEntity<>("Phân công giảng dạy đã được thêm thành công.", HttpStatus.CREATED);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body("Lỗi khi thêm phân công giảng dạy: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAssignment(@PathVariable("id") String assignmentId, @RequestBody TeachingAssignment assignment) {
        if (!teachingAssignmentService.getAssignmentById(assignmentId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            assignment.setAssignmentId(assignmentId);
            teachingAssignmentService.saveAssignment(assignment);
            return new ResponseEntity<>("Phân công giảng dạy đã được cập nhật thành công.", HttpStatus.OK);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật phân công giảng dạy: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAssignment(@PathVariable("id") String assignmentId) {
        if (!teachingAssignmentService.getAssignmentById(assignmentId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            teachingAssignmentService.deleteAssignment(assignmentId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi xóa phân công giảng dạy: " + e.getMessage());
        }
    }

    @PostMapping("/bulk")
    public ResponseEntity<String> addAssignmentsForClass(@RequestBody Map<String, String> request) {
        String className = request.get("className");
        String teacherId = request.get("teacherId");
        String subjectId = request.get("subjectId");

        if (className == null || teacherId == null || subjectId == null) {
            return ResponseEntity.badRequest().body("Thiếu tham số trong yêu cầu.");
        }

        try {
            teachingAssignmentService.addAssignmentsForClass(className, teacherId, subjectId);
            return new ResponseEntity<>("Đã thêm phân công giảng dạy cho tất cả học sinh trong lớp.", HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi thêm phân công giảng dạy: " + e.getMessage());
        }
    }

    @GetMapping("/without-grades")
    public ResponseEntity<List<TeachingAssignment>> getAssignmentsWithoutGrades() {
        try {
            List<TeachingAssignment> assignments = teachingAssignmentService.getAssignmentsWithoutGrades();
            return new ResponseEntity<>(assignments, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
