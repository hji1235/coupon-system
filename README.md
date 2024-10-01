# 🔔 쿠폰 발급 프로젝트 개요
- 개발 기간 : 2024-08-01 ~ 2024-09-13
- 개발 인원 : 1인
  
본 프로젝트는 배달의 민족, 요기요 등과 같은 음식 주문 서비스를 베이스로 여러 조건을 가진 쿠폰을 생성 및 발급하고 사용하는 기능에 초점을 맞춰 개발하였습니다.

# ⚙ 기술 스택
### 개발 환경
1. Java 17
2. Spring Boot
4. JPA
5. AWS EC2, RDS, S3, Code Deploy
6. Gradle

### 기타
1. Rest Docs
2. ERD Cloud
3. Postman
4. GitHub Action

# ❗ 주요 기능
### 쿠폰 조건
  - 발급 주체 (어드민, 브랜드, 매장)
  - 할인 타입 (고정 금액 할인, 퍼센트 할인)
  - 유효기간 (정해진 기간, 발급 후 일정 기간)
  - 쿠폰 사용 가능 시간대 제한
  - 최소 주문 가격
  - 최대 발급 수량
  - 고객별 최대 발급 수량

### 쿠폰 생성
  - 쿠폰 조건을 설정하고 발급 주체별로 쿠폰 생성
      
### 쿠폰 발급 방식
  - 쿠폰 코드
    - 정해진 수량의 회원이 미할당된 쿠폰 생성
    - 회원이 쿠폰 코드를 입력하여 쿠폰 발급(할당)
  - 클릭
    - 이벤트 페이지 및 매장 페이지에서 쿠폰을 클릭하여 발급
    
### 쿠폰 사용
  - 결제 완료 시점에 해당 쿠폰의 사용가능 여부 체크 및 사용처리

# 📌 ERD
<img src="https://github.com/user-attachments/assets/c3019305-cab9-49e4-9931-1ceacaaf1390">

# 🖥 인프라 구조
<img src="https://github.com/user-attachments/assets/4c6de939-8886-434d-a77c-c581d5185fa2">

# 📃 API 문서
API 문서 링크 <http://coupon.cmhapp.click/docs/index.html>
