package com.tdd.demo.service;


import com.tdd.demo.domain.BankAccount;
import com.tdd.demo.repository.AccountRepository;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class AccountService {
    private final AccountRepository repository;
    private final Map<String, ReentrantLock> locks = new ConcurrentHashMap<>(); // 계좌별 Lock 저장소

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public BankAccount createAccount(String accountNumber, int initialDeposit) {
        BankAccount account = new BankAccount(accountNumber, initialDeposit);
        return repository.save(account);
    }

    public void deposit(String accountNumber, int amount) {
        BankAccount account = repository.findByAccountNumber(accountNumber).orElseThrow();
        account.deposit(amount);
    }

    public void withdraw(String accountNumber, int amount) {
        BankAccount account = repository.findByAccountNumber(accountNumber).orElseThrow();
        ReentrantLock lock = locks.computeIfAbsent(accountNumber, key -> new ReentrantLock());

        boolean locked = false;
        try {
            locked = lock.tryLock(500, TimeUnit.MILLISECONDS);
            if (!locked) throw new IllegalStateException("출금 중 다른 요청이 처리 중입니다.");

            if (account.getBalance() < amount) {
                throw new IllegalStateException("잔액 부족");
            }

            Thread.sleep(1000); // 출금에 시간이 걸린다고 가정
            account.withdraw(amount);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (locked) lock.unlock();
        }
    }

    public int getBalance(String accountNumber) {
        return repository.findByAccountNumber(accountNumber).orElseThrow().getBalance();
    }
}
