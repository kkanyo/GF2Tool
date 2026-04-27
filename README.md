# GF2Tool - 게임 정보 위키 플랫폼

> 모던 Java/React 스택을 활용한 풀스택 웹 애플리케이션 포트폴리오 프로젝트

**[English](README_EN.md) | [日本語](README_JA.md)**

## 📋 프로젝트 개요

**GF2Tool**은 모바일 게임 '소녀전선2:망명'과 관련된 데이터 정보를 효율적으로 관리하고 제공하기 위한 웹 플랫폼입니다.
Spring Boot 3.5.11과 React 19를 기반으로 한 현대적인 풀스택 프로젝트로, 마이크로서비스 아키텍처 원칙과 모범 사례를 적용했습니다.

## 🛠 기술 스택

| 계층             | 기술 스택                           |
| ---------------- | ----------------------------------- |
| **Runtime**      | Java 21, Spring Boot 3.5.11         |
| **빌드 도구**    | Gradle                              |
| **데이터베이스** | MySQL 8.0, JPA, QueryDSL 5.0.0      |
| **프론트엔드**   | React 19.2.0, TypeScript 5.9.3      |
| **번들러**       | Vite 7.2.4                          |
| **스타일링**     | Tailwind CSS 4.2.1                  |
| **라우팅**       | React Router 7.12.0                 |
| **HTTP Client**  | Axios                               |
| **다국어**       | i18next, react-i18next (en, ja, ko) |
| **API 문서화**   | Springdoc OpenAPI (Swagger UI)      |
| **테스팅**       | JUnit 5, H2, AssertJ                |
| **코드 품질**    | ESLint, Prettier, TypeScript strict |
| **인프라**       | Docker & Docker Compose             |

## 📁 프로젝트 구조

```
GF2Tool-back/
├── backend/                      # Spring Boot 백엔드 애플리케이션
│   ├── src/
│   │   ├── main/java/com/kkanyo/gf2tool/
│   │   │   ├── domain/          # 비즈니스 로직 (Domain-Driven Design)
│   │   │   ├── global/          # 전역 설정, 예외 처리
│   │   │   └── Gf2toolApplication.java
│   │   ├── main/resources/
│   │   │   ├── application.yml
│   │   │   ├── application-local.yml
│   │   │   └── templates/
│   │   └── test/java/           # 단위 및 통합 테스트
│   ├── build.gradle             # Gradle 설정
│   ├── gradle/wrapper/          # Gradle Wrapper
│   └── HELP.md
│
├── frontend/                     # React/TypeScript 프론트엔드
│   ├── src/
│   │   ├── components/
│   │   │   ├── common/          # 공통 컴포넌트
│   │   │   └── layout/          # 레이아웃 컴포넌트
│   │   ├── pages/
│   │   │   └── doll/            # 도메인 특화 페이지
│   │   ├── api/                 # Axios 및 API 클라이언트
│   │   ├── types/               # TypeScript 타입 정의
│   │   ├── assets/              # 정적 자산
│   │   ├── i18n.ts              # 다국어 설정
│   │   ├── App.tsx
│   │   └── main.tsx
│   ├── public/locales/          # 다국어 리소스 (en, ja, ko)
│   ├── package.json
│   ├── vite.config.ts
│   ├── tsconfig.json
│   └── eslint.config.js
│
├── sql/                         # 데이터베이스 스크립트
├── docker-compose.yml           # MySQL 컨테이너 설정
└── README.md
```

## 🚀 빠른 시작

### 사전 요구사항

- Java 21 (또는 상위 버전)
- Node.js 18+ (npm)
- Docker & Docker Compose
- Git

### 환경 설정

프로젝트 루트 디렉토리에 `.env` 파일을 생성하여 로컬 환경 변수를 관리합니다:

```bash
# .env (버전 관리에서 제외 - .gitignore에 추가)
MYSQL_ROOT_PASSWORD=<로컬_개발용_비밀번호>
MYSQL_USER=<로컬_개발용_사용자>
MYSQL_PASSWORD=<로컬_개발용_비밀번호>
```

### 1️⃣ 데이터베이스 시작

```bash
# MySQL 컨테이너 시작
docker-compose up -d

# 컨테이너 상태 확인
docker-compose ps
```

### 2️⃣ 백엔드 실행

```bash
cd backend

# 빌드
./gradlew build

# 실행 (로컬 환경)
./gradlew bootRun
```

✅ **확인**: `http://localhost:8080`에서 실행됨

**API 문서**: `http://localhost:8080/swagger-ui.html`

### 3️⃣ 프론트엔드 실행

```bash
cd frontend

# 의존성 설치
npm install

# 개발 서버 (Hot Reload)
npm run dev
```

✅ **확인**: `http://localhost:5173`에서 실행됨

## 🏗 아키텍처 및 설계 원칙

### 백엔드 - Domain-Driven Design (DDD)

```
Request
  ↓
Controller Layer (REST API)
  ↓
Service Layer (비즈니스 로직)
  ↓
Repository Layer (JPA + QueryDSL)
  ↓
Database (MySQL)
```

**주요 특징**:

- **Domain Layer**: 핵심 비즈니스 로직 캡슐화
- **QueryDSL**: 타입 안전한 동적 쿼리 작성
- **Global Exception Handler**: 일관된 에러 응답 포맷
- **Validation**: Spring Validation으로 입력 데이터 검증

### 프론트엔드 - 컴포넌트 기반 아키텍처

- **재사용 가능한 컴포넌트**: common, layout 공유 컴포넌트
- **타입 안전성**: TypeScript strict mode 적용
- **중앙화된 API**: Axios 기반 API 클라이언트
- **다국어 지원**: i18next로 국제화
- **반응형 디자인**: Tailwind CSS 유틸리티 프레임워크

## 📊 주요 기능

### API 엔드포인트

- ✅ RESTful 설계 원칙 준수
- ✅ Swagger/OpenAPI 자동 문서화
- ✅ 체계적인 입력 유효성 검증
- ✅ 일관된 에러 처리 및 응답 형식

### 프론트엔드

- ✅ 반응형 UI (Tailwind CSS)
- ✅ 다국어 인터페이스 (i18next)
- ✅ TypeScript 기반 타입 안전성
- ✅ 동적 라우팅 및 SPA 구현

### 개발자 경험 (DX)

- ✅ Hot Module Reload (Vite)
- ✅ 자동 API 클라이언트 코드 생성
- ✅ 코드 포맷팅 자동화 (Prettier)
- ✅ Linting 및 타입 체크 자동화

## 🧪 테스팅 전략

### 백엔드 테스트 실행

```bash
cd backend

# 모든 테스트 실행
./gradlew test

# 특정 테스트 클래스 실행
./gradlew test --tests "com.kkanyo.gf2tool.domain.doll.service.*"
```

**테스트 커버리지**:

- `DollControllerTest`: REST 엔드포인트 테스트
- `DollServiceTest`: 비즈니스 로직 테스트
- `DollRepositoryTest`: JPA 쿼리 테스트
- `DollRepositoryImplTest`: QueryDSL 커스텀 쿼리 테스트
- `GlobalExceptionHandlerTest`: 예외 처리 테스트
- `ValidationExceptionHandlerTest`: 입력 검증 테스트

**테스트 도구**: JUnit 5, H2 (인메모리 DB), AssertJ

### 프론트엔드 코드 품질 보증

```bash
cd frontend

# ESLint 코드 검사
npm run lint

# Prettier 포맷 검사
npm run format:check

# 자동 포맷팅
npm run format
```

## 📚 API 문서

백엔드 실행 중 Swagger UI에서 전체 API 명세를 확인할 수 있습니다　(추가 작업 필요):

```
http://localhost:8080/swagger-ui.html
```

**주요 엔드포인트**:
| 메서드 | 경로 | 설명 |
|--------|------|------|
| GET | `/api/dolls` | 목록 조회 |
| GET | `/api/dolls/{id}` | 상세 조회 |
| POST | `/api/dolls` | 데이터 생성 |
| PUT | `/api/dolls/{id}` | 데이터 수정 |
| DELETE | `/api/dolls/{id}` | 데이터 삭제 |

## 🌐 다국어 지원

프론트엔드는 i18next 기반 국제화 구현:

| 언어    | 코드 | 상태    |
| ------- | ---- | ------- |
| English | en   | ✅ 지원 |
| 日本語  | ja   | ✅ 지원 |
| 한국어  | ko   | ✅ 지원 |

**리소스 위치**: `frontend/public/locales/{언어}/`

## 💡 핵심 학습 포인트

### 백엔드

- **Spring Boot 3.5.11**: 최신 버전의 기능 및 성능 개선
- **Java 21**: 최신 Java 문법 및 성능 최적화
- **QueryDSL**: 타입 안전한 쿼리 구축
- **Test-Driven Development**: 단위/통합 테스트 작성
- **예외 처리**: 일관된 에러 응답 설계 패턴

### 프론트엔드

- **React 19**: 최신 React 기능 및 Hook 패턴
- **TypeScript**: 타입 안전성의 개발 생산성 향상
- **Vite**: 모던 번들러의 성능 이점
- **Tailwind CSS**: 유틸리티 우선 CSS 설계
- **i18n**: 다국어 애플리케이션 구축

### DevOps & 개발 환경

- **Docker & Docker Compose**: 일관된 개발/프로덕션 환경
- **프로필 기반 설정**: 환경별 구성 관리 (local, dev, prod)

## 🔒 보안 및 운영 고려사항

### 개발 환경

- `.env` 파일에서 환경 변수 관리 (버전 관리 제외)
- H2 인메모리 DB를 테스트에 사용하여 데이터 격리
- Docker 네트워크를 통한 내부 통신

## 📝 추가 명령어

### API 클라이언트 코드 자동 생성

백엔드가 실행 중인 상태에서:

```bash
cd frontend
npm run generate-api
```

OpenAPI 명세를 기반으로 TypeScript 클라이언트 코드가 `src/api/generated/` 디렉토리에 생성됩니다.

### 컨테이너 정리

```bash
# 실행 중인 컨테이너 중지
docker-compose down

# 데이터를 포함한 전체 정리
docker-compose down -v
```

## 📈 향후 개선 계획

- [ ] Docker 이미지 자동 빌드 및 배포
- [ ] 자동화된 배포 파이프라인
- [ ] AWS 환경을 이용한 배포

## 📝 라이센스

이 프로젝트는 개인 포트폴리오 프로젝트입니다.

## 🙋 문의

개선 사항이나 질문이 있으면 이슈를 등록해주세요.

---

**Language Versions**: [English](README_EN.md) | [日本語](README_JA.md)
