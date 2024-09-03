package com.example.project.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Subject")
public class Subject {

    @Id
    @Column(name = "Subject_ID", columnDefinition = "CHAR(36)")
    private String subjectId;

    @Column(name = "Subject_Name", nullable = false, length = 255)
    private String subjectName;

    @Column(name = "Semester", nullable = false)
    private int semester;

    @Column(name = "School_Year", nullable = false, length = 45)
    private String schoolYear;

    public Subject() {
        // Generate UUID if not provided
        if (this.subjectId == null) {
            this.subjectId = UUID.randomUUID().toString();
        }
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }
}
