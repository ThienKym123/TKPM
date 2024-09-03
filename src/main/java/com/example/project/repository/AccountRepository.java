package com.example.project.repository;

import com.example.project.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    @Query("SELECT a FROM Account a WHERE (a.email LIKE '%@gvcn.com' OR a.email LIKE '%@gvbm.com') AND a.accountId NOT IN (SELECT t.accountId FROM Teacher t)")
    List<Account> findAvailableAccounts();

    @Query("SELECT a FROM Account a WHERE a.email LIKE '%@hs.com' AND a.accountId NOT IN (SELECT s.accountId FROM Student s)")
    List<Account> findAvailableAccountsForStudent();

    @Query("SELECT a FROM Account a WHERE a.email = :email")
    Optional<Account> findByEmail(@Param("email") String email);
}
