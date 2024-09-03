package com.example.project.controller;

import com.example.project.model.Account;
import com.example.project.service.AccountService;
import com.example.project.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") String accountId) {
        Optional<Account> account = accountService.getAccountById(accountId);
        return account.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody Account account) {
        try {
            Account createdAccount = accountService.saveAccount(account);
            return new ResponseEntity<>("Tài khoản đã được thêm thành công.", HttpStatus.CREATED);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body("Lỗi khi thêm tài khoản: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAccount(@PathVariable("id") String accountId, @RequestBody Account account) {
        if (!accountService.getAccountById(accountId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            account.setAccountId(accountId);
            Account updatedAccount = accountService.saveAccount(account);
            return new ResponseEntity<>("Tài khoản đã được cập nhật thành công.", HttpStatus.OK);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật tài khoản: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable("id") String accountId) {
        if (!accountService.getAccountById(accountId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            accountService.deleteAccount(accountId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi xóa tài khoản: " + e.getMessage());
        }
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam("email") String email, @RequestParam("password") String password) {
        Optional<Account> account = accountService.getAccountByEmail(email);
        if (account.isPresent() && account.get().getPassword().equals(password)) {
            return ResponseEntity.ok("Đăng nhập thành công.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email hoặc mật khẩu không đúng.");
        }
    }

    @GetMapping("/available")
    public ResponseEntity<List<Account>> getAvailableAccounts() {
        List<Account> availableAccounts = accountService.getAvailableAccounts();
        return ResponseEntity.ok(availableAccounts);
    }

    @GetMapping("/available-student")
    public ResponseEntity<List<Account>> getAvailableAccountsForStudent() {
        List<Account> availableAccounts = accountService.getAvailableAccountsForStudent();
        return ResponseEntity.ok(availableAccounts);
    }
}
