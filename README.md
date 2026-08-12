# Rundraw-BE

## 🛠 Tech Stack

### Backend

![Java](https://img.shields.io/badge/Java%2021-007396?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-59666C?style=flat-square&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=flat-square&logo=springsecurity&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=flat-square&logo=jsonwebtokens&logoColor=white)

### Database

![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white)

### API & Documentation

![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=flat-square&logo=swagger&logoColor=black)

### Infrastructure & CI/CD

![AWS EC2](https://img.shields.io/badge/AWS%20EC2-FF9900?style=flat-square&logo=amazonec2&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF?style=flat-square&logo=githubactions&logoColor=white)

---
## 📂 Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── rundraw/
│   │           └── be/
│   │               ├── domain/
│   │               │   ├── auth/          # 로그인/로그아웃
│   │               │   ├── member/        # 사용자
│   │               │   ├── course/        # 러닝 코스
│   │               │   ├── courseDraft/   # 코스 초안
│   │               │   ├── restaurant/    # 음식점
│   │               │   ├── comment/       # 댓글
│   │               │   └── ...
│   │               │
│   │               └── global/
│   │                   ├── config/        # 전역 설정
│   │                   ├── security/      # Spring Security / JWT
│   │                   ├── exception/     # 예외 처리
│   │                   └── response/      # 공통 응답
│   │
│   └── resources/
│       ├── application.yml
│       └── application-prod.yml
│
├── test/
│   └── java/
│
├── .github/
│   └── workflows/
│       └── ci-cd.yml
│
├── build.gradle
├── settings.gradle
├── gradlew
└── gradlew.bat

```
---

## 💾 Git & Commit Convention

### 브랜치 전략

| 타입 | 설명 | 예시 |
| :--- | :--- | :--- |
|**feat**|새로운 기능 개발|`feat/member`|

### 💬 Commit Message

| 태그 | 설명 |
| :--- | :--- |
| `feat` | 새로운 기능 추가 |
| `fix` | 버그 수정 |
| `docs` | 문서 수정 |
| `style` | 코드 포맷팅 |
| `refactor` | 코드 리팩토링 |
| `chore` | 빌드/패키지 설정 |

---

## 📱 프로젝트 소개

**Rundraw**는 사용자가 직접 러닝 코스를 설계하고,  
음성 내비게이션을 통해 경로를 따라 달리며 **GPS Art를 완성할 수 있는 러닝 플랫폼**입니다.

러닝 중 이동 경로와 페이스를 기록하고, 완성한 코스를 다른 사용자들과 공유할 수 있습니다.

---

## ✨ 핵심 기능

- 🎨 **GPS Art 코스 생성**
  - 지도 기반 러닝 코스 설계
  - 원하는 경로를 직접 생성

- 🎧 **음성 내비게이션**
  - 러닝 중 경로를 음성으로 안내
  - 화면을 계속 확인하지 않고도 코스 진행 가능

- 🏃 **러닝 기록**
  - 이동 경로 기록
  - 러닝 페이스 및 운동 데이터 기록

- 📍 **코스 관리**
  - 생성한 코스 저장 및 조회
  - 코스별 상세 정보 제공

- ❤️ **코스 좋아요 / 북마크**
  - 관심 있는 코스 저장
  - 다른 사용자의 코스 탐색

- 💬 **댓글**
  - 코스에 대한 댓글 작성 및 삭제
  - 사용자 간 코스 경험 공유

---

## 🏗 Architecture
<img width="629" height="939" alt="Rundraw Architecture" src="https://github.com/user-attachments/assets/a52b38bf-fac5-4f77-ac57-a5baefc2390c" />

