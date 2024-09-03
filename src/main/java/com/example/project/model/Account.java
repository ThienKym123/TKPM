package com.example.project.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Account")
public class Account {

    @Id
    @Column(name = "Account_ID", columnDefinition = "CHAR(36)")
    private String accountId;

    @Column(name = "Email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "Password", nullable = false, length = 255)
    private String password;

    @Column(name = "Role_Name", nullable = false, length = 255)
    private String roleName;

    public Account() {
        // Generate UUID if not provided
        if (this.accountId == null) {
            this.accountId = UUID.randomUUID().toString();
        }
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
