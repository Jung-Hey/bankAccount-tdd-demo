package com.tdd.demo.repository;

import com.tdd.demo.domain.BankAccount;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public class AccountRepository {
    private final Map<String, BankAccount> db = new ConcurrentHashMap<>();

    public BankAccount save(BankAccount account) {
        if (db.containsKey(account.getAccountNumber())) {
            throw new IllegalArgumentException("이미 존재하는 계좌번호입니다.");
        }
        db.put(account.getAccountNumber(), account);
        return account;
    }

    public Optional<BankAccount> findByAccountNumber(String accountNumber) {
        return Optional.ofNullable(db.get(accountNumber));
    }
}
