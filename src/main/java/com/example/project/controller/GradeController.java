package com.example.project.controller;

import com.example.project.exception.ValidationException;
import com.example.project.model.Grade;
import com.example.project.service.GradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping
    public ResponseEntity<List<Grade>> getAllGrades() {
        List<Grade> grades = gradeService.getAllGrades();
        return new ResponseEntity<>(grades, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grade> getGradeById(@PathVariable("id") String gradeId) {
        return gradeService.getGradeById(gradeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<String> createGrade(@RequestBody Grade grade) {
        try {
            gradeService.saveGrade(grade);
            return new ResponseEntity<>("Điểm đã được thêm thành công.", HttpStatus.CREATED);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body("Lỗi khi thêm điểm: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateGrade(@PathVariable("id") String gradeId, @RequestBody Grade grade) {
        if (!gradeService.getGradeById(gradeId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            grade.setGradeId(gradeId);
            gradeService.saveGrade(grade);
            return new ResponseEntity<>("Điểm đã được cập nhật thành công.", HttpStatus.OK);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật điểm: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGrade(@PathVariable("id") String gradeId) {
        if (!gradeService.getGradeById(gradeId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            gradeService.deleteGrade(gradeId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi xóa điểm: " + e.getMessage());
        }
    }
}
