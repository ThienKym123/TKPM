package com.example.project.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Grade")
public class Grade {

    @Id
    @Column(name = "Grade_ID", columnDefinition = "CHAR(36)")
    private String gradeId;

    @Column(name = "Assignment_ID", columnDefinition = "CHAR(36)", nullable = false)
    private String assignmentId;

    @Column(name = "Oral_Test")
    private Float oralTest;

    @Column(name = "Test_15m_1")
    private Float test15m1;

    @Column(name = "Test_15m_2")
    private Float test15m2;

    @Column(name = "Test_1_Period")
    private Float test1Period;

    @Column(name = "Midterm_Test")
    private Float midtermTest;

    @Column(name = "Final_Test")
    private Float finalTest;

    public Grade() {
        if (this.gradeId == null) {
            this.gradeId = UUID.randomUUID().toString();
        }
    }

    // Getters and Setters
    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Float getOralTest() {
        return oralTest;
    }

    public void setOralTest(Float oralTest) {
        this.oralTest = oralTest;
    }

    public Float getTest15m1() {
        return test15m1;
    }

    public void setTest15m1(Float test15m1) {
        this.test15m1 = test15m1;
    }

    public Float getTest15m2() {
        return test15m2;
    }

    public void setTest15m2(Float test15m2) {
        this.test15m2 = test15m2;
    }

    public Float getTest1Period() {
        return test1Period;
    }

    public void setTest1Period(Float test1Period) {
        this.test1Period = test1Period;
    }

    public Float getMidtermTest() {
        return midtermTest;
    }

    public void setMidtermTest(Float midtermTest) {
        this.midtermTest = midtermTest;
    }

    public Float getFinalTest() {
        return finalTest;
    }

    public void setFinalTest(Float finalTest) {
        this.finalTest = finalTest;
    }
}
