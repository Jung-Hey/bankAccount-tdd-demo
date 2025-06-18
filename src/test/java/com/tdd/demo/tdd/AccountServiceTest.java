package com.tdd.demo.tdd;

import com.tdd.demo.domain.BankAccount;
import com.tdd.demo.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountService.createAccount("123-456", 1000);
    }

    @Test
    @DisplayName("1단계: 계좌 생성 및 초기 입금")
    void createAccountWithInitialDeposit() {
        BankAccount account = accountService.createAccount("456-789", 500);
        assertEquals(500, account.getBalance());
    }

    @Test
    @DisplayName("2단계: 중복 계좌 생성 시 예외 발생")
    void duplicateAccountCreationShouldFail() {
        assertThrows(IllegalArgumentException.class, () -> {
            accountService.createAccount("123-456", 100);
        });
    }

    @Test
    @DisplayName("3단계: 입금 기능")
    void depositTest() {
        accountService.deposit("123-456", 300);
        assertEquals(1300, accountService.getBalance("123-456"));
    }

    @Test
    @DisplayName("4단계: 출금 기능")
    void withdrawTest() {
        accountService.withdraw("123-456", 400);
        assertEquals(600, accountService.getBalance("123-456"));
    }

    @Test
    @DisplayName("5단계: 잔고 조회")
    void balanceTest() {
        int balance = accountService.getBalance("123-456");
        assertEquals(1000, balance);
    }

    @Test
    @DisplayName("6단계: 동시 출금 테스트")
    void concurrentWithdraw() throws InterruptedException {
        Runnable task = () -> {
            try {
                accountService.withdraw("123-456", 800);
            } catch (Exception ignored) {
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        int finalBalance = accountService.getBalance("123-456");
        // 1000원에서 동시에 두 스레드가 800원을 출금하면 하나의 처리만 이루어져야 함
        assertTrue(finalBalance == 200);
    }
}
