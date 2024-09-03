package com.example.project.service;

import com.example.project.exception.ValidationException;
import com.example.project.model.Account;
import com.example.project.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(String accountId) {
        return accountRepository.findById(accountId);
    }

    public Account saveAccount(Account account) {
        validateAccount(account);
        return accountRepository.save(account);
    }

    public void deleteAccount(String accountId) {
        accountRepository.deleteById(accountId);
    }

    public Optional<Account> getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public List<Account> getAvailableAccounts() {
        return accountRepository.findAvailableAccounts();
    }

    public List<Account> getAvailableAccountsForStudent() {
        return accountRepository.findAvailableAccountsForStudent();
    }

    private void validateAccount(Account account) {
        if (account.getEmail() == null || account.getEmail().isEmpty()) {
            throw new ValidationException("Địa chỉ email không được để trống.");
        }
        if (account.getPassword() == null || account.getPassword().isEmpty()) {
            throw new ValidationException("Mật khẩu không được để trống.");
        }
        if (!account.getEmail().matches(".*@(bgh.com|gvcn.com|gvbm.com|hs.com|admin.com)$")) {
            throw new ValidationException("Địa chỉ email không hợp lệ. Phải có đuôi \"@bgh.com\", \"@gvcn.com\", \"@gvbm.com\", \"@hs.com\" hoặc \"@admin.com\".");
        }

        // Check for duplicate email only if it's a new account or a different email
        Optional<Account> existingAccount = accountRepository.findByEmail(account.getEmail());
        if (existingAccount.isPresent() &&
                (account.getAccountId() == null || !existingAccount.get().getAccountId().equals(account.getAccountId()))) {
            throw new ValidationException("Địa chỉ email đã tồn tại.");
        }
    }
}
