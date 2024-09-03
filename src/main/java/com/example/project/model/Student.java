package com.example.project.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Student")
public class Student {

    @Id
    @Column(name = "Student_ID", columnDefinition = "CHAR(36)")
    private String studentId;

    @Column(name = "Name", nullable = false, length = 255)
    private String name;

    @Column(name = "Birthday", nullable = false)
    @Temporal(TemporalType.DATE)  // Use @Temporal for DATE type
    private Date birthday;

    @Column(name = "Gender", nullable = false, length = 10)
    private String gender;

    @Column(name = "Phone", length = 20)
    private String phone;

    @Column(name = "School_Year", nullable = false)
    private int schoolYear;

    @Column(name = "Class_Name", nullable = false, length = 255)  // Updated column name
    private String className;

    @Column(name = "Account_ID", columnDefinition = "CHAR(36)")
    private String accountId;

    public Student() {
        if (this.studentId == null) {
            this.studentId = UUID.randomUUID().toString();
        }
    }

    // Getters and setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
