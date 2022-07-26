# BankingSystem
## 서론
순수 자바를 이용해서 만든 프로그램이다. 단순히 1, 2, 3, ... 을 선택해서 진행하는, 처음으로 자바를 배울 때 만들어볼 법한 간단한 프로그램이다. 이 시점에 이런 프로그램을 만들어 본 이유는, 스프링부트와 JPA를 사용하면서 간단하게 DB를 관리하면서 만약 프레임워크를 사용하지 않는다면 어떨까, 라는 호기심이 생겼고 그렇게 만들어보기 시작했다.

## 환경
- IntelliJ
- Java 16
- MariaDB

## 시나리오
1. 회사 내에서 사용되는 시스템
2. 사내 카페, 식당 등에서 사용되는 포인트 개념으로 환전하고 이용할 수 있도록 함
3. 계좌에서 이체하듯 사번을 가진 다른 임직원에게 선물할 수 있도록 함

## 구현
1. 회원가입
<img width="442" alt="회원가입1" src="https://user-images.githubusercontent.com/75434746/180930287-f7efab46-f2ea-47b1-81da-31fff699ecd2.png">
<img width="783" alt="회원가입1-db" src="https://user-images.githubusercontent.com/75434746/180930297-9a7bd30c-bfcf-4c15-aa4f-dead03305ed1.png">
<img width="417" alt="회원가입2" src="https://user-images.githubusercontent.com/75434746/180930302-b27ddea5-eae3-4274-ae01-36c63220f2f3.png">
<img width="783" alt="회원가입2-db" src="https://user-images.githubusercontent.com/75434746/180930304-e3dbd36e-0cb9-47a5-8331-a18aea8fabfd.png">
<br>
구색 정도는 맞추기 위해 회원가입 시 비밀번호는 인코딩하여 저장되도록 했다.
<br>
<br>
2. 로그인
<br>
<br>
로그인 성공 시
<br>
<img width="404" alt="로그인 성공" src="https://user-images.githubusercontent.com/75434746/180930363-d08478b9-fa8f-4886-b38f-57deba62f242.png">
<br>
로그인 실패 시
<br>
<img width="424" alt="로그인 실패" src="https://user-images.githubusercontent.com/75434746/180930365-de848710-3776-47be-b51e-4ee6b6adadd4.png">

3. 조회

4. 충전

<img width="335" alt="충전" src="https://user-images.githubusercontent.com/75434746/180930815-5d40b5dd-1358-4f2b-bc8c-0276ade8af7b.png">
<img width="779" alt="충전-db" src="https://user-images.githubusercontent.com/75434746/180930822-098aa480-2c11-4797-9a03-af07ca0db7f1.png">

5. 선물(이체)
<br>
잔액 부족 시
<img width="355" alt="선물 - 잔액부족" src="https://user-images.githubusercontent.com/75434746/180930950-33eb10f8-8207-4d7f-8c99-d2477c29a548.png">
<br>
성공 시
<img width="367" alt="선물" src="https://user-images.githubusercontent.com/75434746/180931057-e5b69169-369c-4a0a-bda9-d8aa8566ddf4.png">
<img width="410" alt="선물 받은 것 확인" src="https://user-images.githubusercontent.com/75434746/180931067-ac9f8cbd-1bb3-4eec-84af-be7bd801042c.png">
<img width="780" alt="선물 - db" src="https://user-images.githubusercontent.com/75434746/180931063-8f809fc1-4298-4893-ac04-1fc774639438.png">

## 결론
프레임워크를 왜 사용하는 지 잘 알게 되었다. DB 하나를 연결하려면 connecter를 설치해서 연결해주고, 쿼리문을 하나하나 작성해주고, db에 연결했다가 닫아주고, ... 신경써야하는 부분이 너무 많았다. 지금은 DB만 연결했지만 스프링부트로 웹 개발을 하면서 사용하는 다른 기능들을 직접 구현해보라고 하는 상황을 상상해보면 아득해진다.
개발을 보다 편하게 해주는 도구인 프레임워크만 사용하다가 직접 구현을 해보려고 하니까 번거롭기는 했지만, 왜 프레임워크를 사용하는지 몸소 깨달을 수 있는 좋은 기회였다. 
