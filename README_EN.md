# GF2Tool - Game Wiki Platform

link: https://github.com/kkanyo/GF2Tool

> A full-stack web application portfolio project using modern Java/React stack

**[한국어](README.md) | [日本語](README_JA.md)**

## 📋 Project Overview

**GF2Tool** is a web platform for efficiently managing and serving information related to mobile game "GIRLS' FRONTLINE 2: EXILIUM".
Built with Spring Boot 3.5.11 and React 19, this modern full-stack project applies microservice architecture principles and development best practices.

## 🛠 Technology Stack

| Layer                    | Technologies                        |
| ------------------------ | ----------------------------------- |
| **Runtime**              | Java 21, Spring Boot 3.5.11         |
| **Build Tool**           | Gradle                              |
| **Database**             | MySQL 8.0, JPA, QueryDSL 5.0.0      |
| **Frontend**             | React 19.2.0, TypeScript 5.9.3      |
| **Bundler**              | Vite 7.2.4                          |
| **Styling**              | Tailwind CSS 4.2.1                  |
| **Routing**              | React Router 7.12.0                 |
| **HTTP Client**          | Axios                               |
| **Internationalization** | i18next, react-i18next (en, ja, ko) |
| **API Documentation**    | Springdoc OpenAPI (Swagger UI)      |
| **Testing**              | JUnit 5, H2, AssertJ                |
| **Code Quality**         | ESLint, Prettier, TypeScript strict |
| **Infrastructure**       | Docker & Docker Compose             |

## 📁 Project Structure

```
GF2Tool-back/
├── backend/                      # Spring Boot Backend Application
│   ├── src/
│   │   ├── main/java/com/kkanyo/gf2tool/
│   │   │   ├── domain/          # Business Logic (Domain-Driven Design)
│   │   │   ├── global/          # Global Configuration, Exception Handling
│   │   │   └── Gf2toolApplication.java
│   │   ├── main/resources/
│   │   │   ├── application.yml
│   │   │   ├── application-local.yml
│   │   │   └── templates/
│   │   └── test/java/           # Unit & Integration Tests
│   ├── build.gradle             # Gradle Configuration
│   ├── gradle/wrapper/          # Gradle Wrapper
│   └── HELP.md
│
├── frontend/                     # React/TypeScript Frontend
│   ├── src/
│   │   ├── components/
│   │   │   ├── common/          # Shared Components
│   │   │   └── layout/          # Layout Components
│   │   ├── pages/
│   │   │   └── doll/            # Domain-Specific Pages
│   │   ├── api/                 # Axios & API Client
│   │   ├── types/               # TypeScript Type Definitions
│   │   ├── assets/              # Static Assets
│   │   ├── i18n.ts              # Internationalization Config
│   │   ├── App.tsx
│   │   └── main.tsx
│   ├── public/locales/          # Multi-language Resources (en, ja, ko)
│   ├── package.json
│   ├── vite.config.ts
│   ├── tsconfig.json
│   └── eslint.config.js
│
├── sql/                         # Database Scripts
├── docker-compose.yml           # MySQL Container Configuration
└── README.md
```

## 🚀 Quick Start

### Prerequisites

- Java 21 (or later)
- Node.js 18+ (npm)
- Docker & Docker Compose
- Git

### Environment Setup

Create a `.env` file in the project root directory to manage local environment variables:

```bash
# .env (excluded from version control - add to .gitignore)
MYSQL_ROOT_PASSWORD=<local_development_password>
MYSQL_USER=<local_development_user>
MYSQL_PASSWORD=<local_development_password>
```

### 1️⃣ Start Database

```bash
# Start MySQL container
docker-compose up -d

# Check container status
docker-compose ps
```

### 2️⃣ Run Backend

```bash
cd backend

# Build
./gradlew build

# Run (local environment)
./gradlew bootRun
```

✅ **Verify**: Access `http://localhost:8080`

**API Documentation**: `http://localhost:8080/swagger-ui.html`

### 3️⃣ Run Frontend

```bash
cd frontend

# Install dependencies
npm install

# Start dev server (with Hot Reload)
npm run dev
```

✅ **Verify**: Access `http://localhost:5173`

## 🏗 Architecture & Design Principles

### Backend - Domain-Driven Design (DDD)

```
Request
  ↓
Controller Layer (REST API)
  ↓
Service Layer (Business Logic)
  ↓
Repository Layer (JPA + QueryDSL)
  ↓
Database (MySQL)
```

**Key Features**:

- **Domain Layer**: Encapsulates core business logic
- **QueryDSL**: Type-safe dynamic query construction
- **Global Exception Handler**: Consistent error response format
- **Validation**: Spring Validation for input data validation

### Frontend - Component-Based Architecture

- **Reusable Components**: Shared common and layout components
- **Type Safety**: TypeScript strict mode enabled
- **Centralized API**: Axios-based API client
- **Multi-language Support**: i18next internationalization
- **Responsive Design**: Tailwind CSS utility framework

## 📊 Key Features

### API Endpoints

- ✅ RESTful design principles
- ✅ Automatic Swagger/OpenAPI documentation
- ✅ Systematic input validation
- ✅ Consistent error handling and response format

### Frontend

- ✅ Responsive UI (Tailwind CSS)
- ✅ Multi-language Interface (i18next)
- ✅ TypeScript-based type safety
- ✅ Dynamic Routing & SPA Implementation

### Developer Experience (DX)

- ✅ Hot Module Reload (Vite)
- ✅ Automatic API client code generation
- ✅ Code formatting automation (Prettier)
- ✅ Linting and type checking automation

## 🧪 Testing Strategy

### Backend Testing

```bash
cd backend

# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests "com.kkanyo.gf2tool.domain.doll.service.*"
```

**Test Coverage**:

- `DollControllerTest`: REST endpoint testing
- `DollServiceTest`: Business logic testing
- `DollRepositoryTest`: JPA query testing
- `DollRepositoryImplTest`: QueryDSL custom query testing
- `GlobalExceptionHandlerTest`: Exception handling testing
- `ValidationExceptionHandlerTest`: Input validation testing

**Testing Tools**: JUnit 5, H2 (in-memory DB), AssertJ

### Frontend Code Quality

```bash
cd frontend

# ESLint code analysis
npm run lint

# Prettier format check
npm run format:check

# Auto-fix formatting
npm run format
```

## 📚 API Documentation

Access Swagger UI to view the complete API specification while the backend is running (work in progress):

```
http://localhost:8080/swagger-ui.html
```

**Main Endpoints**:
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/dolls` | List retrieval |
| GET | `/api/dolls/{id}` | Detail retrieval |
| POST | `/api/dolls` | Create data |
| PUT | `/api/dolls/{id}` | Update data |
| DELETE | `/api/dolls/{id}` | Delete data |

## 🌐 Multi-Language Support

Frontend implements i18next-based internationalization:

| Language | Code | Status       |
| -------- | ---- | ------------ |
| English  | en   | ✅ Supported |
| 日本語   | ja   | ✅ Supported |
| 한국어   | ko   | ✅ Supported |

**Resource Location**: `frontend/public/locales/{language}/`

## 💡 Key Learning Points

### Backend

- **Spring Boot 3.5.11**: Latest version features and performance improvements
- **Java 21**: Modern Java syntax and performance optimization
- **QueryDSL**: Type-safe dynamic query construction
- **Test-Driven Development**: Unit and integration test writing
- **Exception Handling**: Consistent error response design patterns

### Frontend

- **React 19**: Latest React features and Hook patterns
- **TypeScript**: Type safety improving development productivity
- **Vite**: Performance benefits of modern bundlers
- **Tailwind CSS**: Utility-first CSS design approach
- **i18n**: Building multi-language applications

### DevOps & Development Environment

- **Docker & Docker Compose**: Consistent development/production environments
- **Profile-Based Configuration**: Environment-specific configuration management

## 🔒 Security & Operational Considerations

### Development Environment

- Environment variable management through `.env` file (excluded from version control)
- H2 in-memory DB for test data isolation
- Docker network for internal communication

## 📝 Additional Commands

### Auto-Generate API Client Code

With backend running:

```bash
cd frontend
npm run generate-api
```

TypeScript client code will be generated in `src/api/generated/` based on OpenAPI specification.

### Container Cleanup

```bash
# Stop running containers
docker-compose down

# Full cleanup including data
docker-compose down -v
```

## 📈 Future Improvements

- [ ] Automated Docker image build and deployment
- [ ] Automated deployment pipeline
- [ ] Deployment on AWS

## 📝 License

This is a personal portfolio project.

## 🙋 Questions & Feedback

Please create an issue if you have any improvements or questions.

---

**Language Versions**: [한국어](README.md) | [日本語](README_JA.md)
