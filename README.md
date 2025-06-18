# 💰 BankAccount TDD Demo

Spring Boot 기반으로 제작한 **계좌 관리 시스템** 예제입니다.  
**TDD(Test-Driven Development)** 방식으로 개발되었으며,  
계좌 생성, 입금, 출금, 잔고 조회, 동시성 처리까지 포함합니다.

---

## ✅ 기능 요구사항 (TDD 단계별 구현)

| 단계 | 기능 설명 |
|------|-----------|
| 1️⃣ | 계좌 생성 시 초기 입금이 가능하다. |
| 2️⃣ | 이미 존재하는 계좌번호로는 중복 생성할 수 없다. |
| 3️⃣ | 계좌에 입금할 수 있어야 한다. |
| 4️⃣ | 계좌에서 출금할 수 있어야 한다. |
| 5️⃣ | 계좌의 현재 잔액을 조회할 수 있어야 한다. |
| 6️⃣ | 동시에 2개 이상의 출금 요청이 와도 하나씩 처리되며, 잔액은 음수가 될 수 없다. |

---

## 🧪 테스트 기반 개발 (TDD)

1. **Red** – 실패하는 테스트 작성  
2. **Green** – 최소한의 코드로 테스트 통과  
3. **Refactor** – 중복 제거 및 리팩토링

완성될때까지 1~3 반복.
모든 요구사항은 테스트 코드(`AccountServiceTest.java`)를 통해 검증되었습니다.

---

## 🧩 기술 스택

- Java 17+
- Spring Boot
- JUnit 5
- ReentrantLock (출금 동시성 처리)
- Map 기반 In-memory Repository

---

## 📁 프로젝트 구조
com.example.bank
```├── domain
│ └── BankAccount.java # 순수 도메인 객체
├── repository
│ └── AccountRepository.java # 계좌 영속성 계층 (Map 기반 In-memory)
├── service
│ └── AccountService.java # 비즈니스 로직 + 동시성 제어
└── test
  └── tdd
    └── AccountServiceTest.java # 단계별 TDD 테스트
```
