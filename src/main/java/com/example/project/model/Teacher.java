package com.example.project.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Teacher")
public class Teacher {

    @Id
    @Column(name = "Teacher_ID", columnDefinition = "CHAR(36)")
    private String teacherId;

    @Column(name = "Name", nullable = false, length = 255)
    private String name;

    @Column(name = "Birthday", nullable = false)
    private String birthday;

    @Column(name = "Gender", nullable = false, length = 10)
    private String gender;

    @Column(name = "Phone", length = 20)
    private String phone;

    @Column(name = "Subject_Name", nullable = false, length = 255)
    private String subjectName;

    @Column(name = "Account_ID", columnDefinition = "CHAR(36)")
    private String accountId;

    public Teacher() {
        if (this.teacherId == null) {
            this.teacherId = UUID.randomUUID().toString();
        }
    }

    // Getters and setters
    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
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

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
