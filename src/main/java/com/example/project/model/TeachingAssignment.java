package com.example.project.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "TeachingAssignment") // Updated table name
public class TeachingAssignment {

    @Id
    @Column(name = "Assignment_ID", columnDefinition = "CHAR(36)")
    private String assignmentId;

    @Column(name = "Teacher_ID", columnDefinition = "CHAR(36)", nullable = false)
    private String teacherId;

    @Column(name = "Subject_ID", columnDefinition = "CHAR(36)", nullable = false)
    private String subjectId;

    @Column(name = "Student_ID", columnDefinition = "CHAR(36)")
    private String studentId;

    public TeachingAssignment() {
        if (this.assignmentId == null) {
            this.assignmentId = UUID.randomUUID().toString();
        }
    }

    // Getters and Setters
    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
