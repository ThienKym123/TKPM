package com.example.project.controller;

import com.example.project.model.Subject;
import com.example.project.service.SubjectService;
import com.example.project.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public ResponseEntity<List<Subject>> getAllSubjects() {
        List<Subject> subjects = subjectService.getAllSubjects();
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubjectById(@PathVariable("id") String subjectId) {
        Optional<Subject> subject = subjectService.getSubjectById(subjectId);
        return subject.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<String> createSubject(@RequestBody Subject subject) {
        try {
            Subject createdSubject = subjectService.saveSubject(subject);
            return new ResponseEntity<>("Môn học đã được thêm thành công.", HttpStatus.CREATED);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body("Lỗi khi thêm môn học: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSubject(@PathVariable("id") String subjectId, @RequestBody Subject subject) {
        if (!subjectService.getSubjectById(subjectId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            subject.setSubjectId(subjectId);
            Subject updatedSubject = subjectService.saveSubject(subject);
            return new ResponseEntity<>("Môn học đã được cập nhật thành công.", HttpStatus.OK);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật môn học: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubject(@PathVariable("id") String subjectId) {
        if (!subjectService.getSubjectById(subjectId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            subjectService.deleteSubject(subjectId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi xóa môn học: " + e.getMessage());
        }
    }
}
