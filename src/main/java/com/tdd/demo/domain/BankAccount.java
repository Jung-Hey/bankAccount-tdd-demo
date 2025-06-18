package com.tdd.demo.domain;



public class BankAccount {

    private final String accountNumber; // 계좌번호
    private int balance; // 잔액

    public BankAccount(String accountNumber, int initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public int getBalance() {
        return balance;
    }

    // 입금 처리
    public void deposit(int amount) {
        this.balance += amount;
    }

    // 출금 처리 (잔액 검증 및 차감만 수행)
    public void withdraw(int amount) {
        if (balance < amount) {
            throw new IllegalStateException("잔액 부족");
        }
        balance -= amount;
    }
}